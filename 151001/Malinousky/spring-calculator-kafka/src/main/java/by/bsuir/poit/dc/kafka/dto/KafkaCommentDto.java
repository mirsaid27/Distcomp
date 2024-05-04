package by.bsuir.poit.dc.kafka.dto;

/**
 * @author Name Surname
 * 
 */
public record KafkaCommentDto(
    long id,
    long tweetId,
    String content) {
}
