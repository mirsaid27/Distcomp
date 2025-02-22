#include "Server.hpp"

int main() {
    Server server{};
    if (!server.initialize()){
        return 1;
    }
    server.start_server();
    return 0;
}