package com.example.rvlab1.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponseTo {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
