#include "CommentHandler.hpp"
    
json CommentHandler::to_json(const Comment& comment) {
    return {
        {"id", comment.id},
        {"articleId", comment.articleId},
        {"content", comment.content},
    };
}

CommentHandler::Comment CommentHandler::from_json(const std::string& data_str) {
    auto data = json::parse(data_str);
    uint64_t id = data.contains("id") 
            ? static_cast<uint64_t>(data["id"]) 
            : lastId++;

    uint64_t articleId = static_cast<uint64_t>(data["articleId"]);
    
    return {
        id, 
        articleId, 
        data["content"]
    };
}

void CommentHandler::handle_post(const httplib::Request& req, httplib::Response& res) {
    auto comment = from_json(req.body);
    comments.push_back(comment);

    res.status = 201;
    res.set_content(to_json(comment).dump(), "application/json");
}

void CommentHandler::handle_get_all(const httplib::Request& req, httplib::Response& res) {
    json comments_json = json::array();
    std::ranges::transform(comments, std::back_inserter(comments_json), [this](const Comment& comment){
        return to_json(comment);
    });

    res.status = 200;
    res.set_content(comments_json.dump(), "application/json");
}

void CommentHandler::handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(comments, [id](const Comment& comment) { 
        return comment.id == id; 
    });

    if (it != comments.end()) {
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Comment with id {} not found", id)}}.dump(), "application/json");
    }
}

void CommentHandler::handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(comments, [id](const Comment& comment) { 
        return comment.id == id; 
    });

    if (it != comments.end()) {
        comments.erase(it);
        res.status = 204;
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Comment with id {} not found", id)}}.dump(), "application/json");
    }
}

void CommentHandler::handle_put(const httplib::Request& req, httplib::Response& res) {
    auto comment = from_json(req.body);
    uint64_t id = comment.id;

    auto it = std::ranges::find_if(comments, [id](const Comment& current) { 
        return current.id == id; 
    });
    
    if (it != comments.end()) {
        *it = comment;
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Comment with id {} not found", id)}}.dump(), "application/json");
    }
}