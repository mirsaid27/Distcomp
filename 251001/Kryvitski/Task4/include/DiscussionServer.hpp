#pragma once
#include "DiscussionHandler.hpp"
#include "KafkaProducer.hpp"
#include "KafkaConsumer.hpp"
#include <memory>
#include <nlohmann/json.hpp>
#include <regex>

template<CassandraEntity ... Ts>
class DiscussionServer final {
public:
    DiscussionServer() noexcept;
    ~DiscussionServer() = default;

    void start_server();
    void stop_server();

private:
    template<CassandraEntity T>
    void register_entity();

    nlohmann::json handle_message(const std::string& entity, const nlohmann::json& request);
    void send_kafka_response(const std::string& correlation_id, const nlohmann::json& response);

private:
    constexpr static auto KAFKA_ADDRESS = "127.0.0.1:9092";

    KafkaProducer m_producer;
    KafkaConsumer m_consumer;
    std::shared_ptr<CassandraController> m_controller;
    std::unordered_map<std::string, std::function<nlohmann::json(const nlohmann::json&)>> m_handlers;
    bool m_running = false;
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
template<CassandraEntity T>
void DiscussionServer<Ts...>::register_entity() {
    auto handler = std::make_shared<DiscussionHandler<T>>(m_controller);
    handler->initialize();
    
    m_handlers[T::entity_name] = [handler](const nlohmann::json& request) -> nlohmann::json {
        try {
            const std::string method = request["method"];
            const std::string path = request["path"];
            const auto payload = request.value("payload", nlohmann::json::object());
            
            if (method == "GET" && path == "/api/v1.0/" + T::entity_name) {
                return handler->handle_get_all(payload);
            } 
            else if (method == "GET" && path.find("/api/v1.0/" + T::entity_name + "/") == 0) {
                std::string id = path.substr(path.find_last_of('/') + 1);
                return handler->handle_get_one(id, payload);
            }
            else if (method == "POST" && path == "/api/v1.0/" + T::entity_name) {
                return handler->handle_post(payload);
            }
            else if (method == "PUT" && path == "/api/v1.0/" + T::entity_name) {
                return handler->handle_put(payload);
            }
            else if (method == "DELETE" && path.find("/api/v1.0/" + T::entity_name + "/") == 0) {
                std::string id = path.substr(path.find_last_of('/') + 1);
                return handler->handle_delete(id, payload);
            }
            
            return {{"status", 404}, {"error", "Method or path not found"}};
        } catch (const std::exception& e) {
            return {{"status", 400}, {"error", e.what()}};
        }
    };
}

template<CassandraEntity ... Ts>
void DiscussionServer<Ts...>::start_server() {
    m_running = true;
    while (m_running) {
        auto msg = m_consumer.consume(100);
        if (msg.has_value()) {
            try {
                auto json = msg.value();
                if (!json.contains("method") || !json.contains("entity")) {
                    std::cerr << "ERROR: Missing required fields in JSON" << std::endl;
                    continue;
                }

                std::string entity = json["entity"];
                std::string correlation_id = json.value("correlation_id", "");

                if (json.contains("body") && json["body"].is_string()) {
                    try {
                        std::cout << "Parsing 'body' field..." << std::endl;
                        json["body"] = nlohmann::json::parse(json["body"].get<std::string>());
                    } catch (const std::exception& e) {
                        std::cerr << "ERROR: Failed to parse 'body' field: " << e.what() << std::endl;
                        continue;
                    }
                }

                auto response = handle_message(entity, json);
                send_kafka_response(correlation_id, response);
            } catch (const std::exception& e) {
                nlohmann::json error_response = {
                    {"status", 400},
                    {"error", std::string("Invalid request format: ") + e.what()}
                };
                send_kafka_response("", error_response);
            }
        }
    }
}


template<CassandraEntity ... Ts>
void DiscussionServer<Ts...>::stop_server() {
    m_running = false;
}

template<CassandraEntity ... Ts>
nlohmann::json DiscussionServer<Ts...>::handle_message(
    const std::string& entity, 
    const nlohmann::json& request)
{
    if (m_handlers.find(entity) != m_handlers.end()) {
        return m_handlers[entity](request);
    }
    return {{"status", 404}, {"error", "Entity not found"}};
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