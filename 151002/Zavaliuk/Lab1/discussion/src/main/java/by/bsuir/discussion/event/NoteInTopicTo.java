package by.bsuir.discussion.event;

import by.bsuir.discussion.model.entity.NoteState;

import java.util.Locale;

public record NoteInTopicTo(
        Long id,
        Long newsId,
        Locale country,
        String content,
        NoteState state
) {
}