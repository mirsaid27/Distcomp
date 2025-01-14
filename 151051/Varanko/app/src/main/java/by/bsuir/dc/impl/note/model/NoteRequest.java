package by.bsuir.dc.impl.note.model;

import jakarta.validation.constraints.Size;

public record NoteRequest(
        long id,
        long storyId,
        @Size(min = 2, max = 2048, message = "Content must be between 2 and 2048 characters")
        String content
){}
