package com.example.rest.entity;

import lombok.Data;



@Data
public class User {

    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;

}
