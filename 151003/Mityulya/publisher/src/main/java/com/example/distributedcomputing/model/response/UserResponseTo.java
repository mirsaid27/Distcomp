package com.example.distributedcomputing.model.response;

public record UserResponseTo(
        Long id,
        String login,
        String firstname,
        String lastname) {
}

