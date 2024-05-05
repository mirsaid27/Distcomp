package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.UserRequestTo;
import com.example.distributedcomputing.model.response.UserResponseTo;
import com.example.distributedcomputing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseTo create(@RequestBody UserRequestTo userRequestTo) {
        return userService.save(userRequestTo);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserResponseTo update(@RequestBody UserRequestTo userRequestTo) {
        return userService.update(userRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
       userService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<UserResponseTo> getAll() {
        return userService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public UserResponseTo getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}
