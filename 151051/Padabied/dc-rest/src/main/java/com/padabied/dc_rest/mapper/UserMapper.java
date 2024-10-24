package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.User;
import com.padabied.dc_rest.model.dto.requests.UserRequestTo;
import com.padabied.dc_rest.model.dto.responses.UserResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")  // Важно указать componentModel = "spring"
public interface UserMapper {

    UserResponseTo toResponse(User user);

    @Mapping(target = "id", ignore = true)  // Игнорируем поле id, так как оно генерируется
    User toEntity(UserRequestTo userRequestDto);
}