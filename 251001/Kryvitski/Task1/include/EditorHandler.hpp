#pragma once
#include "EntityHandler.hpp"
#include <vector>

class EditorHandler : public EntityHandler {
public:
    struct Editor {
        uint64_t id{};
        std::string login{};
        std::string password{};
        std::string firstname{};
        std::string lastname{};
    };

    json to_json(const Editor& editor);
    Editor from_json(const std::string& data_str);

    void handle_post(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_all(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_put(const httplib::Request& req, httplib::Response& res) override;

private:
    std::vector<Editor> editors;
    uint64_t lastId{1};
};