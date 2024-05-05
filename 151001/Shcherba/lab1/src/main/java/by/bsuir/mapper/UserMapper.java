package by.bsuir.mapper;

import by.bsuir.dto.UserRequestTo;
import by.bsuir.dto.UserResponseTo;
import by.bsuir.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User UserRequestToUser(UserRequestTo UserRequestTo);

    UserResponseTo UserToUserResponse(User User);
}

