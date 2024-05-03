package com.example.rvlab1.mapper;

import com.example.rvlab1.model.dto.request.UserRequestTo;
import com.example.rvlab1.model.dto.response.UserResponseTo;
import com.example.rvlab1.model.entity.UserEntity;
import com.example.rvlab1.model.service.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User mapToBO(UserEntity userEntity);

    UserEntity mapToEntity(User user);

    User mapToBO(UserRequestTo userRequestTo);

    UserResponseTo mapToResponseTo(User user);

    @InheritConfiguration
    void updateUserRequestToToUserBo(UserRequestTo userRequestTo, @MappingTarget User user);
}
