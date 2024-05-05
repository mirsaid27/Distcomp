package by.bsuir.services;

import by.bsuir.dto.UserRequestTo;
import by.bsuir.dto.UserResponseTo;
import by.bsuir.entities.User;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.DuplicationException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.UserListMapper;
import by.bsuir.mapper.UserMapper;
import by.bsuir.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userDao;
    @Autowired
    UserListMapper userListMapper;

    public UserResponseTo getUserById(@Min(0) Long id) throws NotFoundException {
        Optional<User> user = userDao.findById(id);
        return user.map(value -> userMapper.userToUserResponse(value)).orElseThrow(() -> new NotFoundException("User not found!", 40004L));
    }

    public List<UserResponseTo> getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<User> users = userDao.findAll(pageable);
        return userListMapper.toUserResponseList(users.toList());
    }

    public UserResponseTo saveUser(@Valid UserRequestTo user) throws DuplicationException{
        User userToSave = userMapper.userRequestToUser(user);
        if (userDao.existsByLogin(userToSave.getLogin())){
            throw new DuplicationException("Login duplication", 40005L);
        }
        return userMapper.userToUserResponse(userDao.save(userToSave));
    }

    public void deleteUser(@Min(0) Long id) throws DeleteException {
        if (!userDao.existsById(id)) {
            throw new DeleteException("User not found!", 40004L);
        } else {
            userDao.deleteById(id);
        }
    }

    public UserResponseTo updateUser(@Valid UserRequestTo user) throws UpdateException {
        User userToUpdate = userMapper.userRequestToUser(user);
        if (!userDao.existsById(userToUpdate.getId())){
            throw new UpdateException("User not found!", 40004L);
        } else {
            return userMapper.userToUserResponse(userDao.save(userToUpdate));
        }
    }

    public UserResponseTo getUserByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        User user = userDao.findUserByTweetId(tweetId);
        return userMapper.userToUserResponse(user);
    }
}
