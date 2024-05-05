package by.bsuir.mapper;

import by.bsuir.dto.UserRequestTo;
import by.bsuir.dto.UserResponseTo;
import by.bsuir.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequestTo userRequestTo);

    UserResponseTo userToUserResponse(User user);
}

