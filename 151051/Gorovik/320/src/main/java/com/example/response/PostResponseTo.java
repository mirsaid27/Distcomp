package com.example.response;

public record PostResponseTo(
        Long id,
        Long newsId,
        String content
) {
}