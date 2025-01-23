package com.rmakovetskij.dc_rest.model.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditorRequestTo {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 64)
    private String login;

    @NotBlank
    @Size(min = 8, max = 128)
    private String password;

    @NotBlank
    @Size(min = 2, max = 64)
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 64)
    private String lastname;
}
