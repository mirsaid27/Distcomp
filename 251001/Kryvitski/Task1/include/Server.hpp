#include "EditorHandler.hpp"
#include "ArticleHandler.hpp"
#include "CommentHandler.hpp"
#include "LabelHandler.hpp"
#include <memory>

class Server final {
public:
    Server() = default;
    ~Server() = default;

    bool initialize();
    void start_server();

private:
    void register_entity(const std::string& name, std::unique_ptr<EntityHandler> handler);

    constexpr static int PORT = 24110;
    httplib::Server m_server;
    std::unordered_map<std::string, std::unique_ptr<EntityHandler>> handlers;
};