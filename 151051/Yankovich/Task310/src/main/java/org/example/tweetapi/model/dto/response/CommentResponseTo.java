package org.example.tweetapi.model.dto.response;

import lombok.Data;

@Data
public class CommentResponseTo {
    private Long id;
    private Long tweetId;
    private String content;
}

