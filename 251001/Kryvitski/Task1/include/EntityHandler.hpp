#pragma once
#include <httplib.h>
#include <nlohmann/json.hpp>
#include <format>

using json = nlohmann::json;

std::string time_to_iso8601(const std::tm& tm);
std::tm iso8601_to_time(const std::string& iso8601);

class EntityHandler {
public:
    virtual ~EntityHandler() = default;

    virtual void handle_post(const httplib::Request& req, httplib::Response& res) = 0;
    virtual void handle_get_all(const httplib::Request& req, httplib::Response& res) = 0;
    virtual void handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) = 0;
    virtual void handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) = 0;
    virtual void handle_put(const httplib::Request& req, httplib::Response& res) = 0;
};