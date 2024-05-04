package com.example.rvlab1.service.impl;

import com.example.rvlab1.exception.ServiceErrorCode;
import com.example.rvlab1.exception.ServiceException;
import com.example.rvlab1.model.service.User;
import com.example.rvlab1.service.UserService;
import com.example.rvlab1.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public List<User> getAll() {
        return userStorage.findAll();
    }

    @Override
    public User saveUser(User user) {
        validateUserToSave(user);
        return userStorage.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userStorage.findById(userId)
                .orElseThrow(() -> new ServiceException("Пользователь не найден", ServiceErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public void deleteUser(User user) {
        userStorage.delete(user);
    }

    private void validateUserToSave(User user) {
        if (user.getLogin().length() < 2 || user.getLogin().length() > 64
            || user.getPassword().length() < 8 || user.getPassword().length() > 128
            || user.getFirstname().length() < 2 || user.getFirstname().length() > 64
            || user.getLastname().length() < 2 || user.getLastname().length() > 64
        ) {
            throw new ServiceException("Пользователь не валиден", ServiceErrorCode.BAD_REQUEST);
        }
        if (Objects.isNull(user.getId()) && userStorage.existsByLogin(user.getLogin())) {
            throw new ServiceException("Duplicate login", ServiceErrorCode.DUPLICATE_USER_LOGIN);
        }
    }
}
