package by.ryabchikov.tweet_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    private Long id;
    private Creator creator;
    private String title;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
}
