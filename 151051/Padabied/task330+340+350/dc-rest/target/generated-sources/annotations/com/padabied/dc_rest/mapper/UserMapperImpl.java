package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.User;
import com.padabied.dc_rest.model.dto.requests.UserRequestTo;
import com.padabied.dc_rest.model.dto.responses.UserResponseTo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T21:44:20+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseTo toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseTo userResponseTo = new UserResponseTo();

        userResponseTo.setId( user.getId() );
        userResponseTo.setLogin( user.getLogin() );
        userResponseTo.setFirstname( user.getFirstname() );
        userResponseTo.setLastname( user.getLastname() );

        return userResponseTo;
    }

    @Override
    public User toEntity(UserRequestTo userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( userRequestDto.getLogin() );
        user.setPassword( userRequestDto.getPassword() );
        user.setFirstname( userRequestDto.getFirstname() );
        user.setLastname( userRequestDto.getLastname() );

        return user;
    }
}
