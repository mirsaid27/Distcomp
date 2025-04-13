#pragma once
#include "DiscussionHandler.hpp"
#include "KafkaProducer.hpp"
#include "KafkaConsumer.hpp"
#include <memory>
#include <nlohmann/json.hpp>
#include <regex>
#include <thread>
#include <tuple>

template<CassandraEntity ... Ts>
class DiscussionServer final {
public:
    DiscussionServer() noexcept;
    ~DiscussionServer();

    void start_server();
    void stop_server();

private:
    void kafka_poll();

    template<CassandraEntity T>
    void register_entity();

    std::shared_ptr<DiscussionHandlerBase> get_handler_by_entity(const std::string& entity);
    void send_kafka_response(const std::string& correlation_id, const nlohmann::json& response);

private:
    constexpr static auto KAFKA_ADDRESS = "127.0.0.1:9092";
    constexpr static auto SERVER_ADDRESS = "0.0.0.0";
    constexpr static uint16_t SERVER_PORT = 24130;

    httplib::Server m_server;
    KafkaProducer m_producer;
    KafkaConsumer m_consumer;
    std::shared_ptr<CassandraController> m_controller;
    std::tuple<std::shared_ptr<DiscussionHandler<Ts>>...> m_handlers;
    std::atomic<bool> m_running{false};
    std::thread m_kafka_thread;
};


template<CassandraEntity ... Ts>
DiscussionServer<Ts...>::DiscussionServer() noexcept {
    m_controller = std::make_shared<CassandraController>();
    m_controller->initialize();
    m_producer.initialize(KAFKA_ADDRESS, "out");
    m_consumer.initialize(KAFKA_ADDRESS, "in");
    (register_entity<Ts>(), ...);
}

template<CassandraEntity ... Ts>
DiscussionServer<Ts...>::~DiscussionServer() {
    stop_server();
}

template<CassandraEntity ... Ts>
template<CassandraEntity T>
void DiscussionServer<Ts...>::register_entity() {
    auto handler = std::make_shared<DiscussionHandler<T>>(m_controller);
    handler->initialize();
    std::get<std::shared_ptr<DiscussionHandler<T>>>(m_handlers) = handler;

    const std::string entity_name = T::entity_name;
    const std::string path = "/api/v1.0/" + entity_name;

    m_server.Post(path, [this, handler](const httplib::Request& req, httplib::Response& res) {
        try {
            nlohmann::json json;
            json["method"] = "POST";
            json["body"] = nlohmann::json::parse(req.body);
            auto response = handler->handle_post(json);
            res.status = response["status"];
            res.set_content("", "application/json");
        } catch (const std::exception& e) {
            res.status = 500;
            res.set_content(nlohmann::json{{"error", e.what()}}.dump(), "application/json");
        }
    });

    m_server.Get(path + "/(\\d+)", [this, handler](const httplib::Request& req, httplib::Response& res) {
        try {
            uint64_t id = std::stoull(req.matches[1]);
            auto response = handler->handle_get_one(id);
            res.status = response["status"];
            if (response.contains("data")){
                res.set_content(response["data"].dump(), "application/json");
            }
            else{
                res.set_content("{}", "application/json");
            }
        } catch (const std::exception& e) {
            res.status = 500;
            res.set_content(nlohmann::json{{"error", e.what()}}.dump(), "application/json");
        }
    });

    m_server.Get(path, [this, handler](const httplib::Request& req, httplib::Response& res) {
        try {
            auto response = handler->handle_get_all();
            res.status = response["status"];
            res.set_content(response["data"].dump(), "application/json");
        } catch (const std::exception& e) {
            res.status = 500;
            res.set_content(nlohmann::json{{"error", e.what()}}.dump(), "application/json");
        }
    });

    m_server.Put(path, [this, handler](const httplib::Request& req, httplib::Response& res) {
        try {
            auto json = nlohmann::json::parse(req.body);
            auto response = handler->handle_put(json);
            res.status = response["status"];
            res.set_content("", "application/json");
        } catch (const std::exception& e) {
            res.status = 500;
            res.set_content(nlohmann::json{{"error", e.what()}}.dump(), "application/json");
        }
    });

    m_server.Delete(path + "/(\\d+)", [this, handler](const httplib::Request& req, httplib::Response& res) {
        try {
            uint64_t id = std::stoull(req.matches[1]);
            auto response = handler->handle_delete(id);
            res.status = response["status"];
            res.set_content(response.value("data", "{}"), "application/json");
        } catch (const std::exception& e) {
            res.status = 500;
            res.set_content(nlohmann::json{{"error", e.what()}}.dump(), "application/json");
        }
    });
}

template<CassandraEntity ... Ts>
void DiscussionServer<Ts...>::start_server() {
    m_running = true;
    m_kafka_thread = std::thread([this]() {
        kafka_poll();
    });

    m_server.listen(SERVER_ADDRESS, SERVER_PORT);
}

template<CassandraEntity ... Ts>
std::shared_ptr<DiscussionHandlerBase> DiscussionServer<Ts...>::get_handler_by_entity(const std::string& entity) {
    std::shared_ptr<DiscussionHandlerBase> result = nullptr;
    (
        [&] {
            if (Ts::entity_name == entity) {
                result = std::get<std::shared_ptr<DiscussionHandler<Ts>>>(m_handlers);
            }
        }(),
        ...
    );
    return result;
}

template<CassandraEntity ... Ts>
void DiscussionServer<Ts...>::kafka_poll() {
    while (m_running) {
        auto msg = m_consumer.consume(100);
        if (msg.has_value()) {
            auto json = msg.value();
            std::string correlation_id = json["correlation_id"];
            std::string method = json["method"];
            std::string entity = json["entity"];

            auto handler = get_handler_by_entity(entity);
            if (!handler) {
                send_kafka_response(correlation_id, {{"status", 404}, {"error", "Entity not found"}});
                continue;
            }

            nlohmann::json response;
            try {
                if (method == "POST") {
                    std::cout << "DISCUSSION POST METHOD" << std::endl;
                    response = handler->handle_post(json["body"]);
                } else if (method == "GET") {
                    if (json.contains("path_id")) {
                        uint64_t id = json["path_id"];
                        response = handler->handle_get_one(id);
                    } else {
                        response = handler->handle_get_all();
                    }
                } else if (method == "PUT") {
                    std::cout << "[PUT BODY]" << std::endl;
                    std::cout << json["body"].dump(4) << std::endl;
                    response = handler->handle_put(json["body"]);
                } else if (method == "DELETE") {
                    uint64_t id = json["path_id"];
                    std::cout << "DELETE ID = " << id << std::endl;
                    response = handler->handle_delete(id);
                } else {
                    response = {{"status", 405}, {"error", "Method not allowed"}};
                }
            } catch (const std::exception& e) {
                response = {
                    {"status", 400},
                    {"error", std::string("Invalid request format: ") + e.what()}
                };
            }
            send_kafka_response(correlation_id, response);
        }
    }
}

template<CassandraEntity ... Ts>
void DiscussionServer<Ts...>::stop_server() {
    m_running = false;
    if (m_kafka_thread.joinable()) {
        m_kafka_thread.join();
    }
    m_server.stop();
}

template<CassandraEntity ... Ts>
void DiscussionServer<Ts...>::send_kafka_response(
    const std::string& correlation_id,
    const nlohmann::json& response)
{
    nlohmann::json message = {
        {"correlation_id", correlation_id},
        {"response", response},
        {"timestamp", std::time(nullptr)}
    };
    m_producer.sendMessage(message);
}