package ru.bsuir.dto.response;

public record EditorResponseTo(Long id,
                               String login,
                               String password,
                               String firstname,
                               String lastname) {
}
