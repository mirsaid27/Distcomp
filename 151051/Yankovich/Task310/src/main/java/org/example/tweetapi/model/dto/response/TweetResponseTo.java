package org.example.tweetapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponseTo {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}
