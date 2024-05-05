package org.example.dc.controllers;

import org.example.dc.model.UserDto;
import org.example.dc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1.0/users")
public class UsersController {
    @Autowired
    private UserService userService;
    @Cacheable
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }
    @Cacheable
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto, BindingResult br, HttpServletResponse response) {
        UserDto user = new UserDto();
        if(br.hasErrors()) {
            response.setStatus(400);
            return user;
        }
        response.setStatus(201);
        try {
            user = userService.createUser(userDto);
        } catch (Exception e) {
            response.setStatus(403);
            user = userService.getUsers().stream()
                    .filter(ed -> ed.getLogin().equals(userDto.getLogin()))
                    .findFirst().get();
        }
        return user;
    }

    @PutMapping()
    public UserDto updateUser(@RequestBody @Valid UserDto userDto, BindingResult br, HttpServletResponse response) {
        if(br.hasErrors()) {
            response.setStatus(401);
            return userService.getUserById(userDto.getId());
        }
        try {
            return userService.updateUser(userDto);
        } catch (Exception e) {
            response.setStatus(402);
        }
        return userService.getUserById(userDto.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            userService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }
}