package org.example.tweetapi.model.dto.response;

import lombok.Data;

@Data
public class AuthorResponseTo {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
}

