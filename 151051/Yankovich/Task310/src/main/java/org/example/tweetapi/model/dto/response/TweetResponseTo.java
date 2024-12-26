package org.example.tweetapi.model.dto.response;

import lombok.Data;

@Data
public class TweetResponseTo {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String created;
    private String modified;
}

