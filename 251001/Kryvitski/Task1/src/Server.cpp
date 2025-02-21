#include "Server.hpp"

bool Server::initialize() {
    register_entity("editors", std::make_unique<EditorHandler>());
    register_entity("articles", std::make_unique<ArticleHandler>());
    register_entity("comments", std::make_unique<CommentHandler>());
    register_entity("labels", std::make_unique<LabelHandler>());
    return true;
}

void Server::start_server() {
    for (const auto& [entity, handler] : handlers) {
        m_server.Post("/api/v1.0/" + entity, [&, this](const httplib::Request& req, httplib::Response& res) {
            handler->handle_post(req, res);
        });

        m_server.Get("/api/v1.0/" + entity, [&, this](const httplib::Request& req, httplib::Response& res) {
            handler->handle_get_all(req, res);
        });

        m_server.Get("/api/v1.0/" + entity + "/(\\d+)", [&, this](const httplib::Request& req, httplib::Response& res) {
            uint64_t id = std::stoull(req.matches[1]);
            handler->handle_get_one(req, res, id);
        });

        m_server.Delete("/api/v1.0/" + entity + "/(\\d+)", [&, this](const httplib::Request& req, httplib::Response& res) {
            uint64_t id = std::stoull(req.matches[1]);
            handler->handle_delete(req, res, id);
        });

        m_server.Put("/api/v1.0/" + entity, [&, this](const httplib::Request& req, httplib::Response& res) {
            handler->handle_put(req, res);
        });
    }

    m_server.listen("0.0.0.0", PORT);
}

void Server::register_entity(const std::string& name, std::unique_ptr<EntityHandler> handler) {
    handlers[name] = std::move(handler);
}