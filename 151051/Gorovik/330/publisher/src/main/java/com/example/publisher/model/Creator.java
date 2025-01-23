package com.example.model;

import com.example.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@SuperBuilder
public class Creator extends AEntity {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}