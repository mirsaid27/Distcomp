package com.example.rvlab1.controller;

import com.example.rvlab1.mapper.UserMapper;
import com.example.rvlab1.model.dto.request.UserRequestTo;
import com.example.rvlab1.model.dto.request.UserRequestWithIdTo;
import com.example.rvlab1.model.dto.response.UserResponseTo;
import com.example.rvlab1.model.service.User;
import com.example.rvlab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseTo>> getUsers() {
        return ResponseEntity.ok(userService.getAll().stream().map(userMapper::mapToResponseTo).toList());
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseTo> createUser(@RequestBody UserRequestTo userRequestTo) {
        User user = userService.saveUser(userMapper.mapToBO(userRequestTo));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.mapToResponseTo(user));
    }

    @DeleteMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userService.findById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseTo> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userMapper.mapToResponseTo(userService.findById(userId)));
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseTo> updateUserById(@RequestBody UserRequestWithIdTo userRequestTo) {
        User user = userService.findById(userRequestTo.getId());
        userMapper.updateUserRequestToToUserBo(userRequestTo, user);
        user = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.mapToResponseTo(user));
    }
}
