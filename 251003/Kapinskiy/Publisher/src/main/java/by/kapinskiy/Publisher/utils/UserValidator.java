package by.kapinskiy.Publisher.utils;

import by.kapinskiy.Publisher.DTOs.Requests.UserRequestDTO;
import by.kapinskiy.Publisher.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasFieldErrors()){
            UserRequestDTO user = (UserRequestDTO) target;
            if (usersService.existsByLogin(user.getLogin())){
                errors.rejectValue("login", null, "User with such login already exists");
            }
        }
    }
}
