#include "PublisherServer.hpp"
#include "DiscussionServer.hpp"
#include "Entities/Editor.hpp"
#include "Entities/Article.hpp"
#include "Entities/Comment.hpp"
#include "Entities/Label.hpp"
#include <thread>

int main() {
    PublisherServer<Editor, Article, Label> publisher;
    DiscussionServer<Comment> discussion;

    auto discussion_thread = std::thread([&](){
        discussion.start_server();
    });
    
    publisher.redirect("comments", 24130);
    publisher.start_server();
    discussion_thread.join();
    return 0;
}