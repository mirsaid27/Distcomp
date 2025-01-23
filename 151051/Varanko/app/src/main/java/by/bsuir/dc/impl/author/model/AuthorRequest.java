package by.bsuir.dc.impl.author.model;

import jakarta.validation.constraints.Size;

public record AuthorRequest(
        long id,
        @Size(min = 2, max = 64, message = "Login must be between 2 and 64 characters")
        String login,
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        String password,
        @Size(min = 2, max = 64, message = "First name must be between 2 and 64 characters")
        String firstname,
        @Size(min = 2, max = 64, message = "Last name must be between 2 and 64 characters")
        String lastname
){}
