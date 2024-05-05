package com.example.lab2.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorRequestTo {
    int id;
    @Size(min = 2, max = 64)
    String login;
    String password;
    String firstname;
    String lastname;
}
