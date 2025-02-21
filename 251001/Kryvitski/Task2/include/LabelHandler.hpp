#pragma once
#include "EntityHandler.hpp"

class LabelHandler : public EntityHandler {
public:
    struct Label {
        uint64_t id{};
        std::string name{};
    };

    json to_json(const Label& label);
    Label from_json(const std::string& data_str);

    void handle_post(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_all(const httplib::Request& req, httplib::Response& res) override;
    void handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) override;
    void handle_put(const httplib::Request& req, httplib::Response& res) override;

private:
    std::vector<Label> labels;
    uint64_t lastId{1};
};