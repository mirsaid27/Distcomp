#include "EditorHandler.hpp"
#include <algorithm>

json EditorHandler::to_json(const Editor& editor) {
    return {
        {"id", editor.id},
        {"login", editor.login},
        {"password", editor.password},
        {"firstname", editor.firstname},
        {"lastname", editor.lastname}
    };
}

EditorHandler::Editor EditorHandler::from_json(const std::string& data_str) {
    auto data = json::parse(data_str);

    uint64_t id = data.contains("id") 
            ? static_cast<uint64_t>(data["id"]) 
            : lastId++; 

    return {
        id, 
        data["login"], 
        data["password"], 
        data["firstname"], 
        data["lastname"]
    };
}

void EditorHandler::handle_post(const httplib::Request& req, httplib::Response& res) {
    auto editor = from_json(req.body);
    editors.push_back(editor);

    res.status = 201;
    res.set_content(to_json(editor).dump(), "application/json");
}

void EditorHandler::handle_get_all(const httplib::Request& req, httplib::Response& res) {
    json editors_json = json::array();

    std::ranges::transform(editors, std::back_inserter(editors_json), [this](const Editor& editor){
        return to_json(editor);
    });

    res.status = 200;
    res.set_content(editors_json.dump(), "application/json");
}

void EditorHandler::handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(editors, [id](const Editor& editor) { 
        return editor.id == id; 
    });

    if (it != editors.end()) {
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Editor with id {} not found", id)}}.dump(), "application/json");
    }
}

void EditorHandler::handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(editors, [id](const Editor& editor) { 
        return editor.id == id; 
    });

    if (it != editors.end()) {
        editors.erase(it);
        res.status = 204;
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Editor with id {} not found", id)}}.dump(), "application/json");
    }
}

void EditorHandler::handle_put(const httplib::Request& req, httplib::Response& res) {
    auto editor = from_json(req.body);

    if (editor.login.size() <= 1){
        res.status = 404;
        res.set_content(json{{"error", "Invalid login"}}.dump(), "application/json");
        return;
    }

    auto it = std::ranges::find_if(editors, [id = editor.id](const Editor& editor){
        return editor.id == id;
    });

    if (it != editors.end()) {
        *it = std::move(editor);
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", "Editor with id not found"}}.dump(), "application/json");
    }
}