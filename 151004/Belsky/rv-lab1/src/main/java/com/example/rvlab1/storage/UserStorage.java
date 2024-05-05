package com.example.rvlab1.storage;

import com.example.rvlab1.model.service.User;

public interface UserStorage extends CRUDStorage<User, Long> {
    boolean existsByLogin(String login);

    boolean existsById(Long id);
}
