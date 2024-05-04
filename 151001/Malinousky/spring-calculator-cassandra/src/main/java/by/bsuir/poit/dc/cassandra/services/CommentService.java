package by.bsuir.poit.dc.cassandra.services;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.CommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.PresenceDto;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
public interface CommentService {
    CommentDto save(UpdateCommentDto dto);

    CommentDto update(long commentId, UpdateCommentDto dto);

    PresenceDto delete(long commentId);

    CommentDto getById(long commentId);

    @Deprecated
    List<CommentDto> getAll();
    List<CommentDto> getAllByTweetId(long tweetId);
}
