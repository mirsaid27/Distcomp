package by.kapinskiy.Task310.controllers;

import by.kapinskiy.Task310.DTOs.Requests.UserRequestDTO;
import by.kapinskiy.Task310.DTOs.Responses.UserResponseDTO;
import by.kapinskiy.Task310.models.User;
import by.kapinskiy.Task310.services.UsersService;
import by.kapinskiy.Task310.utils.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final Mapper mapper;
    @Autowired
    public UsersController(UsersService usersService, Mapper mapper) {
        this.usersService = usersService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User user = mapper.userRequestToUser(userRequestDTO);
        return new ResponseEntity<>(mapper.userToUserResponse(usersService.save(user)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        usersService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<User> users = usersService.findAll();
        return new ResponseEntity<>(mapper.usersToUserResponses(users), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {
        User user = usersService.findById(id);
        return new ResponseEntity<>(mapper.userToUserResponse(user), HttpStatus.OK);
    }

    // Because of test limitations we don't require id in the path, and also we send user in the response,
    // but we shouldn't do that
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User updatedUser = usersService.update(mapper.userRequestToUser(userRequestDTO));
        return new ResponseEntity<>(mapper.userToUserResponse(updatedUser), HttpStatus.OK);
    }

    // Rest version
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO){
        usersService.update(id, mapper.userRequestToUser(userRequestDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
