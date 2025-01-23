package org.example.tweetapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseTo {
    private Long id;
    private String content;
    private Long tweetId;
}
