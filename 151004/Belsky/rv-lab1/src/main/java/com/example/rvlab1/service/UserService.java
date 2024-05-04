package com.example.rvlab1.service;

import com.example.rvlab1.model.service.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User saveUser(User user);

    User findById(Long userId);

    void deleteUser(User user);
}
