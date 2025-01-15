package ru.bsuir.dto.request;

import jakarta.validation.constraints.Size;


public record CommentRequestTo (Long id,
                               Long tweetId,
                               @Size(min = 4, max = 2048)
                               String content) {
}
