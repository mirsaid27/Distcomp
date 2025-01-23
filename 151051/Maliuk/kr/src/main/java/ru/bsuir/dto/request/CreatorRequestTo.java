package ru.bsuir.dto.request;

import jakarta.validation.constraints.Size;

public record CreatorRequestTo(Long id,
                               @Size(min = 2, max = 64)
                               String login,
                               @Size(min = 2, max = 128)
                               String password,
                               @Size(min = 2, max = 64)
                               String firstname,
                               @Size(min = 2, max = 64)
                               String lastname) {
}
