#include "ArticleHandler.hpp"
    
json ArticleHandler::to_json(const Article& article) {
    return {
        {"id", article.id},
        {"title", article.title},
        {"content", article.content},
        {"editorId", article.editorId},
        {"created", time_to_iso8601(article.created)},
        {"modified", time_to_iso8601(article.modified)}
    };
}

ArticleHandler::Article ArticleHandler::from_json(const std::string& data_str) {
    auto data = json::parse(data_str);
    std::tm now = {};
    std::time_t t = std::time(nullptr);
    gmtime_r(&t, &now);

    uint64_t id = data.contains("id") 
            ? static_cast<uint64_t>(data["id"]) 
            : lastId++;

    std::tm created = data.contains("created") 
            ? iso8601_to_time(data["created"]) 
            : now;

    std::tm modified = data.contains("modified") 
            ? iso8601_to_time(data["modified"]) 
            : now;

    return {
        id, 
        data["title"],
        data["content"], 
        data["editorId"],
        created,
        modified
    };
}

void ArticleHandler::handle_post(const httplib::Request& req, httplib::Response& res) {
    auto article = from_json(req.body);
    articles.push_back(article);

    res.status = 201;
    res.set_content(to_json(article).dump(), "application/json");
}

void ArticleHandler::handle_get_all(const httplib::Request& req, httplib::Response& res) {
    json articles_json = json::array();
    std::ranges::transform(articles, std::back_inserter(articles_json), [this](const Article& article){
        return to_json(article);
    });

    res.status = 200;
    res.set_content(articles_json.dump(), "application/json");
}

void ArticleHandler::handle_get_one(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(articles, [id](const Article& article) { 
        return article.id == id; 
    });

    if (it != articles.end()) {
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Article with id {} not found", id)}}.dump(), "application/json");
    }
}

void ArticleHandler::handle_delete(const httplib::Request& req, httplib::Response& res, uint64_t id) {
    auto it = std::ranges::find_if(articles, [id](const Article& article) { 
        return article.id == id; 
    });

    if (it != articles.end()) {
        articles.erase(it);
        res.status = 204;
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Article with id {} not found", id)}}.dump(), "application/json");
    }
}

void ArticleHandler::handle_put(const httplib::Request& req, httplib::Response& res) {
    auto article = from_json(req.body);
    if (article.title.size() <= 1) {
        res.status = 404;
        res.set_content(json{{"error", "Invalid title"}}.dump(), "application/json");
        return;
    }
    
    uint64_t id = article.id;
    auto it = std::ranges::find_if(articles, [id](const Article& current) { 
        return current.id == id; 
    });
    
    if (it != articles.end()) {
        *it = article;
        res.status = 200;
        res.set_content(to_json(*it).dump(), "application/json");
    } else {
        res.status = 404;
        res.set_content(json{{"error", std::format("Article with id {} not found", id)}}.dump(), "application/json");
    }
}