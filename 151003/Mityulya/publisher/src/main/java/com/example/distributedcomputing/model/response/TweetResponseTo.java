package com.example.distributedcomputing.model.response;

public record TweetResponseTo(
        Long id,
        String title,
        String content,
        Long userId
) {
}
