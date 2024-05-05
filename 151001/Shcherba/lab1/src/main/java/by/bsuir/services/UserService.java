package by.bsuir.services;

import by.bsuir.dao.UserDao;
import by.bsuir.dto.UserRequestTo;
import by.bsuir.dto.UserResponseTo;
import by.bsuir.entities.User;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.UserListMapper;
import by.bsuir.mapper.UserMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {
    @Autowired
    UserMapper UserMapper;
    @Autowired
    UserDao UserDao;
    @Autowired
    UserListMapper UserListMapper;

    public UserResponseTo getUserById(@Min(0) Long id) throws NotFoundException{
        Optional<User> User = UserDao.findById(id);
        return User.map(value -> UserMapper.UserToUserResponse(value)).orElseThrow(() -> new NotFoundException("User not found!", 40004L));
    }

    public List<UserResponseTo> getUsers() {
        return UserListMapper.toUserResponseList(UserDao.findAll());
    }

    public UserResponseTo saveUser(@Valid UserRequestTo User) {
        User UserToSave = UserMapper.UserRequestToUser(User);
        return UserMapper.UserToUserResponse(UserDao.save(UserToSave));
    }

    public void deleteUser(@Min(0) Long id) throws DeleteException {
        UserDao.delete(id);
    }

    public UserResponseTo updateUser(@Valid UserRequestTo User) throws UpdateException {
        User UserToUpdate = UserMapper.UserRequestToUser(User);
        return UserMapper.UserToUserResponse(UserDao.update(UserToUpdate));
    }

}
