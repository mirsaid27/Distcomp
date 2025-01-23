package com.example.response;

public record ErrorResponseTo(
        int code,
        String message,
        String[] errMessages
) {
}