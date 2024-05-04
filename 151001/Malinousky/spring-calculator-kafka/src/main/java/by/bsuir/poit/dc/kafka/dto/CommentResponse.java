package by.bsuir.poit.dc.kafka.dto;

import lombok.Builder;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Builder
public record CommentResponse(
    ResponseEvent status,
    List<KafkaCommentDto> list
) implements StatusResponse {

    public static CommentResponse withStatus(ResponseEvent event) {
	return new CommentResponse(event, null);
    }
}
