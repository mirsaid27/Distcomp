package org.example.dc.services.impl;

import org.example.dc.model.UserDto;
import org.example.dc.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class ArrayListUserService implements UserService {
    private List<UserDto> users = new ArrayList();
    private static int id = 1;

    @Override
    public List<UserDto> getUsers() {
        return users;
    }

    @Override
    public UserDto getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(id++);
        users.add(userDto);
        return userDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        UserDto user = users.stream()
                .filter(ed -> ed.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        users.remove(user);
        return true;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserDto user = users.stream()
                .filter(ed -> ed.getId() == userDto.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        user.setId(userDto.getId());
        user.setLastname(userDto.getLastname());
        user.setFirstname(userDto.getFirstname());
        user.setPassword(userDto.getPassword());
        user.setLogin(userDto.getLogin());
        return user;
    }
}
