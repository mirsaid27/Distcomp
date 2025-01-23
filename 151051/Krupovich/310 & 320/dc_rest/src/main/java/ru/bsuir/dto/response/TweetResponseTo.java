package ru.bsuir.dto.response;


import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public record TweetResponseTo (Long id,
                               Long editorId,
                               String title,
                               String content,
                               LocalDateTime created,
                               LocalDateTime modified){
}
