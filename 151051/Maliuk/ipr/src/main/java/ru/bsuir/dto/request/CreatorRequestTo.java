package ru.bsuir.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatorRequestTo {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 64)
    String login;

    @NotBlank
    @Size(min = 2, max = 128)
    String password;

    @NotBlank
    @Size(min = 2, max = 64)
    String firstname;

    @NotBlank
    @Size(min = 2, max = 64)
    String lastname;
}
