package com.padabied.dc_rest.service;

import com.padabied.dc_rest.mapper.UserMapper;
import com.padabied.dc_rest.model.User;
import com.padabied.dc_rest.model.dto.requests.UserRequestTo;
import com.padabied.dc_rest.model.dto.responses.UserResponseTo;
import com.padabied.dc_rest.repository.interfaces.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @CacheEvict(value = "users", allEntries = true)
    public UserResponseTo createUser(UserRequestTo userRequestDto) {
            User user = userMapper.toEntity(userRequestDto);
            user = userRepository.save(user);
            return userMapper.toResponse(user);
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponseTo getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Cacheable(value = "usersList")
    public List<UserResponseTo> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = {"users", "usersList"}, key = "#id", allEntries = true)
    public UserResponseTo updateUser(Long id, UserRequestTo userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userRequestDto.getLogin().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login must be at least 2 characters long");
        }

        existingUser.setLogin(userRequestDto.getLogin());
        existingUser.setPassword(userRequestDto.getPassword());
        existingUser.setFirstname(userRequestDto.getFirstname());
        existingUser.setLastname(userRequestDto.getLastname());

        userRepository.save(existingUser);
        return userMapper.toResponse(existingUser);
    }

    @CacheEvict(value = {"users", "usersList"}, key = "#id", allEntries = true)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}