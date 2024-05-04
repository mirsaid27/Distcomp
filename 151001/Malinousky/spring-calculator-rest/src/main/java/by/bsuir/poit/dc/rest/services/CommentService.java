package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.CommentDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
public interface CommentService {
    @Deprecated
    List<CommentDto> getAll();

    CommentDto save(UpdateCommentDto dto, long tweetId, @Nullable String language);

    CommentDto update(long commentId, @Valid UpdateCommentDto dto, @Nullable String language);

    PresenceDto delete(long commentId);

    CommentDto getById(long commentId);

    List<CommentDto> getAllByTweetId(long tweetId);

}
