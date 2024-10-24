package com.padabied.dc_rest.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseTo {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
}
