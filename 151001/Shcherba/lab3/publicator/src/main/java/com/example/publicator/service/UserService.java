package com.example.publicator.service;

import com.example.publicator.dto.UserRequestTo;
import com.example.publicator.dto.UserResponseTo;
import com.example.publicator.exception.DuplicateException;
import com.example.publicator.exception.NotFoundException;
import com.example.publicator.mapper.UserListMapper;
import com.example.publicator.mapper.UserMapper;
import com.example.publicator.model.User;
import com.example.publicator.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Validated
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserListMapper userListMapper;

    public UserResponseTo create(@Valid UserRequestTo userRequestTo){
        User user = userMapper.userRequestToUser(userRequestTo);
        if(userRepository.existsByLogin(user.getLogin())){
            throw new DuplicateException("Login duplication", 403);
        }
        return userMapper.userToUserResponse(userRepository.save(user));
    }
    public List<UserResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc")){
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        }
        else{
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        }
        Page<User> res = userRepository.findAll(p);
        return userListMapper.toUserResponseList(res.toList());
    }

    public UserResponseTo read(@Min(0) int id) throws NotFoundException {
        if(userRepository.existsById(id)){
            UserResponseTo cr = userMapper.userToUserResponse(userRepository.getReferenceById(id));
            return cr;
        }
        else
            throw new NotFoundException("User not found", 404);
    }
    public UserResponseTo update(@Valid UserRequestTo user,  @Min(0) int id){
        if(userRepository.existsById(id)){
            User cr =  userMapper.userRequestToUser(user);
            cr.setId(id);
            return userMapper.userToUserResponse(userRepository.save(cr));
        }
        else
            throw new NotFoundException("User not found", 404);
    }
    public boolean delete(@Min(0) int id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("User not found", 404);
    }


}
