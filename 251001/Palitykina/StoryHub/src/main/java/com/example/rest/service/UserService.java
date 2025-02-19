package com.example.rest.service;

import com.example.rest.dto.requestDto.UserRequestTo;
import com.example.rest.dto.responseDto.UserResponseTo;
import com.example.rest.mapper.UserMapper;
import com.example.rest.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserResponseTo> getAll() {
        return userRepo
                .getAll()
                .map(userMapper::ToUserResponseTo)
                .toList();
    }
    public UserResponseTo get(long id) {
        return userRepo
                .get(id)
                .map(userMapper::ToUserResponseTo)
                .orElse(null);
    }
    public UserResponseTo create(UserRequestTo input) {
        return userRepo
                .create(userMapper.ToUser(input))
                .map(userMapper::ToUserResponseTo)
                .orElseThrow();
    }
    public UserResponseTo update(UserRequestTo input) {
        return userRepo
                .update(userMapper.ToUser(input))
                .map(userMapper::ToUserResponseTo)
                .orElseThrow();
    }
    public boolean delete(long id) {
        return userRepo.delete(id);
    }
}
