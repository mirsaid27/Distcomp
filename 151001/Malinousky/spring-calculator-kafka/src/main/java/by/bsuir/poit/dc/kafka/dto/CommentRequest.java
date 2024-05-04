package by.bsuir.poit.dc.kafka.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;

/**
 * @author Name Surname
 * 
 */
@Builder
public record CommentRequest(
    @NonNull RequestEvent event,
    Long id,
    @Nullable KafkaUpdateCommentDto dto
) {
    public static CommentRequest withId(@NonNull RequestEvent event, long id) {
	return new CommentRequest(event, id, null);
    }

    public static CommentRequest empty(@NonNull RequestEvent event) {
	return new CommentRequest(event, null, null);
    }
}
