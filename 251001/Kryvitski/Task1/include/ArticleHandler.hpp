#pragma once
#include "EntityHandler.hpp"

class ArticleHandler : public EntityHandler {
public:
    struct Article {
        uint64_t id{};
        std::string title{};
        std::string content{};
        uint64_t editorId{};
        std::tm created{};
        std::tm modified{};
    };

    json to_json(const Article& article);
    Article from_json(const std::string& data_str);

    void handle_post(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_all(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_put(const httplib::Request& req, httplib::Response& res) override;

private:
    std::vector<Article> articles;
    uint64_t lastId{1};
};