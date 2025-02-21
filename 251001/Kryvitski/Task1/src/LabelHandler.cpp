#include "LabelHandler.hpp"
    
json LabelHandler::to_json(const Label& label) {
    return {
        {"id", label.id},
        {"name", label.name},
    };
}

LabelHandler::Label LabelHandler::from_json(const std::string& data_str) {
    auto data = json::parse(data_str);

    uint64_t id = data.contains("id") 
            ? static_cast<uint64_t>(data["id"]) 
            : lastId++;

    return {
        id, 
        data["name"]
    };
}

void LabelHandler::handle_post(const httplib::Request& req, httplib::Response& res) {
    auto label = from_json(req.body);
    labels.push_back(label);

    res.status = 201;
    res.set_content(to_json(label).dump(), "application/json");
}

void LabelHandler::handle_get_all(const httplib::Request& req, httplib::Response& res) {
    json articles_json = json::array();
    std::ranges::transform(labels, std::back_inserter(articles_json), [this](const Label& label){
        return to_json(label);
    });

    res.status = 200;
    res.set_content(articles_json.dump(), "application/json");
}

void LabelHandler::handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(labels, [id](const Label& label) { 
        return label.id == id; 
    });
    
    if (it != labels.end()) {
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Label with id {} not found", id)}}.dump(), "application/json");
    }
}

void LabelHandler::handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(labels, [id](const Label& label) { 
        return label.id == id; 
    });

    if (it != labels.end()) {
        labels.erase(it);
        res.status = 204;
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Label with id {} not found", id)}}.dump(), "application/json");
    }
}

void LabelHandler::handle_put(const httplib::Request& req, httplib::Response& res) {
    auto label = from_json(req.body);
    
    uint64_t id = label.id;
    auto it = std::ranges::find_if(labels, [id](const Label& label) { 
        return label.id == id; 
    });
    
    if (it != labels.end()) {
        *it = label;
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Label with id {} not found", id)}}.dump(), "application/json");
    }
}