#pragma once
#include "EntityHandler.hpp"

class CommentHandler : public EntityHandler {
public:
    struct Comment {
        uint64_t id{};
        uint64_t articleId{};
        std::string content{};
    };

    json to_json(const Comment& comment);
    Comment from_json(const std::string& data_str);

    void handle_post(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_all(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_put(const httplib::Request& req, httplib::Response& res) override;

private:
    std::vector<Comment> comments;
    uint64_t lastId{1};
};