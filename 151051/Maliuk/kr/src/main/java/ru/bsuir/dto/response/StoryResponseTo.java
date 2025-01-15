package ru.bsuir.dto.response;


import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public record StoryResponseTo(Long id,
                              Long creatorId,
                              String title,
                              String content,
                              LocalDateTime created,
                              LocalDateTime modified){
}
