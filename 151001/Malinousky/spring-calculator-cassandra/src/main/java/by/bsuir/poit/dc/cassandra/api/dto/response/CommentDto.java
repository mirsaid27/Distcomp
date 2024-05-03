package by.bsuir.poit.dc.cassandra.api.dto.response;

/**
 * @author Name Surname
 * 
 */
public record CommentDto(
    long id,
    long tweetId,
    String content) {
}
