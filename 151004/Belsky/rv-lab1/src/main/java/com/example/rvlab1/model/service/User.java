package com.example.rvlab1.model.service;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
