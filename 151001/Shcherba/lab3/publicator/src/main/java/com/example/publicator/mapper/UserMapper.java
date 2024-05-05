package com.example.publicator.mapper;

import com.example.publicator.dto.UserRequestTo;
import com.example.publicator.dto.UserResponseTo;
import com.example.publicator.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequestTo userRequestTo);

    UserResponseTo userToUserResponse(User user);
}
