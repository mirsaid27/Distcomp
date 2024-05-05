package by.bsuir.publisher.event;

public record NoteOutTopicTo(
        Long id,
        Long newsId,
        String content
) {
}