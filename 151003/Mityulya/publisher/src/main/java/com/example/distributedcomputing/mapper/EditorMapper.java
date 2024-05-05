package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.User;
import com.example.distributedcomputing.model.request.UserRequestTo;
import com.example.distributedcomputing.model.response.UserResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EditorMapper {
    User dtoToEntity(UserRequestTo userRequestTo);
    List<User> dtoToEntity(Iterable<User> editors);

    UserResponseTo entityToDto(User user);

    List<UserResponseTo> entityToDto(Iterable<User> editors);
}
