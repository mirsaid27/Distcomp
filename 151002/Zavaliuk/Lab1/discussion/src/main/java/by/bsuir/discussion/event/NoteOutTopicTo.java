package by.bsuir.discussion.event;

public record NoteOutTopicTo(
        Long id,
        Long newsId,
        String content
) {
}