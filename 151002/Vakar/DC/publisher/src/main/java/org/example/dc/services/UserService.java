package org.example.dc.services;

import org.example.dc.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserById(int id);
    UserDto createUser(UserDto userDto);
    boolean delete(int id) throws Exception;
    UserDto updateUser(UserDto userDto);
}
