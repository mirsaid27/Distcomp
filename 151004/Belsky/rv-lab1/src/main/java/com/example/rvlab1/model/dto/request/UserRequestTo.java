package com.example.rvlab1.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRequestTo {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
