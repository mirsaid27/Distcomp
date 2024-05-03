package com.example.rvlab1.storage.impl;

import com.example.rvlab1.mapper.UserMapper;
import com.example.rvlab1.model.service.User;
import com.example.rvlab1.repository.UserRepository;
import com.example.rvlab1.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserStorageImpl implements UserStorage {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToBO)
                .toList();
    }

    @Override
    public User save(User user) {
        return userMapper.mapToBO(
                userRepository.save(userMapper.mapToEntity(user))
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userMapper::mapToBO);
    }

    @Override
    public void delete(User user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
