package com.padabied.dc_rest.controller;

import com.padabied.dc_rest.model.dto.requests.UserRequestTo;
import com.padabied.dc_rest.model.dto.responses.UserResponseTo;
import com.padabied.dc_rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseTo> createUser(@RequestBody @Valid UserRequestTo userRequestDto) {
            UserResponseTo createdUser = userService.createUser(userRequestDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseTo> getUserById(@PathVariable Long id) {
        UserResponseTo user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseTo>> getAllUsers() {
        List<UserResponseTo> usersResponseDtos = userService.getAllUsers();
        return new ResponseEntity<>(usersResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseTo> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestTo userRequestDto) {
        UserResponseTo updatedUser = userService.updateUser(id, userRequestDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponseTo> updateUser(@RequestBody @Valid UserRequestTo userRequestDto) {
        if (userRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserResponseTo updatedUser = userService.updateUser(userRequestDto.getId(), userRequestDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
