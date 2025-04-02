#pragma once
#include <concepts>
#include "Entities/Entity.hpp"
#include "CassandraController.hpp"
#include <nlohmann/json.hpp>

template<CassandraEntity T>
class DiscussionHandler {
public:
    explicit DiscussionHandler(std::shared_ptr<CassandraController> controller) noexcept;
    virtual ~DiscussionHandler() = default;

    void initialize();
    
    nlohmann::json handle_post(const nlohmann::json& payload);
    nlohmann::json handle_get_all(const nlohmann::json& payload);
    nlohmann::json handle_get_one(const std::string& id, const nlohmann::json& payload);
    nlohmann::json handle_delete(const std::string& id, const nlohmann::json& payload);
    nlohmann::json handle_put(const nlohmann::json& payload);

private:
    std::shared_ptr<CassandraController> m_controller;
};

template <CassandraEntity T>
inline DiscussionHandler<T>::DiscussionHandler(std::shared_ptr<CassandraController> controller) noexcept
    : m_controller(controller)
{
}

template <CassandraEntity T>
void DiscussionHandler<T>::initialize()
{
    auto result = m_controller->create_table<T>();
}

template<CassandraEntity T>
nlohmann::json DiscussionHandler<T>::handle_post(const nlohmann::json& payload) {
    try {
        T entity = T::from_json(payload);
        
        if (m_controller->insert(entity)) {
            return {
                {"status", 201},
                {"data", entity.to_json()}
            };
        } else {
            return {
                {"status", 403},
                {"error", "Insert failure"}
            };
        }
    }
    catch (...) {
        return {
            {"status", 400},
            {"error", "Invalid JSON format"}
        };
    }
}

template<CassandraEntity T>
nlohmann::json DiscussionHandler<T>::handle_get_all(const nlohmann::json& payload) {
    try {
        std::vector<T> entities = m_controller->get_all<T>();
        nlohmann::json entities_json = nlohmann::json::array();
        
        std::ranges::transform(entities, std::back_inserter(entities_json), [](const auto& entity){
            return entity.to_json();
        });
        
        return {
            {"status", 200},
            {"data", entities_json}
        };
    } catch (...) {
        return {
            {"status", 500},
            {"error", "Internal server error"}
        };
    }
}

template<CassandraEntity T>
nlohmann::json DiscussionHandler<T>::handle_get_one(const std::string& id, const nlohmann::json& payload) {
    try {
        std::optional<T> entity = m_controller->get_by_id<T>(id);
        
        if (entity.has_value()) {
            return {
                {"status", 200},
                {"data", entity.value().to_json()}
            };
        }
        return {
            {"status", 404},
            {"error", "Id not found: " + id}
        };
    } catch (...) {
        return {
            {"status", 500},
            {"error", "Internal server error"}
        };
    }
}

template<CassandraEntity T>
nlohmann::json DiscussionHandler<T>::handle_delete(const std::string& id, const nlohmann::json& payload) {
    try {
        if (m_controller->delete_by_id<T>(id)) {
            return {
                {"status", 204}
            };
        }
        return {
            {"status", 404},
            {"error", "Delete failed, id not found"}
        };
    } catch (...) {
        return {
            {"status", 500},
            {"error", "Internal server error"}
        };
    }
}

template<CassandraEntity T>
nlohmann::json DiscussionHandler<T>::handle_put(const nlohmann::json& payload) {
    try {
        T entity = T::from_json(payload);
        m_controller->update_by_id(entity);
        
        return {
            {"status", 200},
            {"data", entity.to_json()}
        };
    } catch (...) {
        return {
            {"status", 400},
            {"error", "Invalid JSON format"}
        };
    }
}