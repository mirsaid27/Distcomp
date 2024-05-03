package com.example.publisherservice.dto.responseDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreatorResponseTo implements Serializable {

    private Long id;

    private String login;

    private String password;

    private String firstname;

    private String lastname;
}
