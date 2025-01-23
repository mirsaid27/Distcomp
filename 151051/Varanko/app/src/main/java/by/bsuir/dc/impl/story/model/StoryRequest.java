package by.bsuir.dc.impl.story.model;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record StoryRequest(
        long id,
        long authorId,
        @Size(min = 2, max = 64, message = "Title must be between 2 and 64 characters")
        String title,
        @Size(min = 4, max = 2048, message = "Content must be between 4 and 64 characters")
        String content,
        LocalDateTime createdDatetime,
        LocalDateTime modifiedDatetime
){}
