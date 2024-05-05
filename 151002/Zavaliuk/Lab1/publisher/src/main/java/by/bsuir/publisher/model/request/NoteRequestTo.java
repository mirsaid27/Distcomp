package by.bsuir.publisher.model.request;

import org.hibernate.validator.constraints.Length;

public record NoteRequestTo(
        Long id,
        Long newsId,
        @Length(min = 2, max = 2048)
        String content) 
{}
