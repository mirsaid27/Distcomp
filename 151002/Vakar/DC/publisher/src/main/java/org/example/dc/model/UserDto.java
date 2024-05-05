package org.example.dc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    @Length(min = 2, max = 64)
    private String login;
    @Length(min = 8, max = 128)
    private String password;
    @Length(min = 2, max = 64)
    private String firstname;
    @Length(min = 2, max = 64)
    private String lastname;
}
