package by.bsuir.poit.dc.kafka.dto;

/**
 * @author Name Surname
 * 
 */
public interface KafkaCommentMapper<IN, OUT> {
    IN unwrapRequest(KafkaUpdateCommentDto dto);

    KafkaCommentDto buildResponse(OUT dto);
}
