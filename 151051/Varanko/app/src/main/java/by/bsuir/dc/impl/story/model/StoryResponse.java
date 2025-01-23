package by.bsuir.dc.impl.story.model;

import java.time.LocalDateTime;

public record StoryResponse(
        long id,
        long authorId,
        String title,
        String content,
        LocalDateTime created_datetime,
        LocalDateTime modified_datetime
){}
