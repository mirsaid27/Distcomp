package com.example.publicator.mapper;

import com.example.publicator.dto.UserRequestTo;
import com.example.publicator.dto.UserResponseTo;
import com.example.publicator.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<User> toUserList(List<UserRequestTo> users);

    List<UserResponseTo> toUserResponseList(List<User> users);
}
