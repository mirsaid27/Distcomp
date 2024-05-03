package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.dto.mappers.CommentMapper;
import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.CommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.cassandra.dao.CommentByIdRepository;
import by.bsuir.poit.dc.cassandra.dao.CommentByTweetRepository;
import by.bsuir.poit.dc.cassandra.model.CommentById;
import by.bsuir.poit.dc.cassandra.model.CommentByTweet;
import by.bsuir.poit.dc.cassandra.services.ModerationService;
import by.bsuir.poit.dc.cassandra.services.CommentService;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.context.IdGenerator;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Name Surname
 * 
 */
@Slf4j
@Component
@RequiredArgsConstructor
@CatchLevel(DataAccessException.class)
public class CommentServiceImpl implements CommentService {
    private final CommentByIdRepository commentByIdRepository;
    private final CommentByTweetRepository commentByTweetRepository;
    private final CommentMapper commentMapper;
    private final IdGenerator idGenerator;
    private final ModerationService moderationService;

    @Override
    @Transactional
    @CatchThrows(
	call = "newCommentModifyingException",
	args = "commentId")
    public PresenceDto delete(long commentId) {
	Optional<CommentById> commentOptional = commentByIdRepository.findById(commentId);
	if (commentOptional.isPresent()) {
	    var comment = commentOptional.get();
	    commentByTweetRepository.deleteByIdAndTweetId(commentId, comment.getTweetId());
	    commentByIdRepository.deleteById(commentId);
	}
	return PresenceDto.wrap(commentOptional.isPresent());
    }


    @Override
    @Transactional
    @CatchThrows(
	call = "newCommentAlreadyPresentException")
    public CommentDto save(UpdateCommentDto rawDto) {
	UpdateCommentDto dto = moderationService.prepareSave(rawDto);
	long id = idGenerator.nextLong();
	CommentById entity = commentMapper.toEntityById(id, dto);
	CommentByTweet commentByTweet = commentMapper.toEntityByTweet(id, dto);
	CommentById saved = commentByIdRepository.save(entity);
	CommentByTweet _ = commentByTweetRepository.save(commentByTweet);
	return commentMapper.toDto(saved);
    }

    @Override
    public List<CommentDto> getAllByTweetId(long tweetId) {
	List<CommentByTweet> entities = commentByTweetRepository.findByTweetId(tweetId);
	return commentMapper.toDtoListFromCommentByTweet(entities);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newCommentModifyingException",
	args = "commentId")
    public CommentDto update(long commentId, UpdateCommentDto rawDto) {
	UpdateCommentDto dto = moderationService.prepareUpdate(rawDto);
	CommentById commentById = commentByIdRepository
				.findById(commentId)
				.orElseThrow(() -> newCommentNotFountException(commentId));
	CommentByTweet commentByTweet = commentByTweetRepository
				    .findByIdAndTweetId(commentId, commentById.getTweetId())
				    .orElseThrow(() -> newCommentNotFountException(commentId));
	CommentById _ = commentMapper.partialUpdate(commentById, dto);
	CommentByTweet _ = commentMapper.partialUpdate(commentByTweet, dto);
	CommentById saved = commentByIdRepository.save(commentById);
	CommentByTweet _ = commentByTweetRepository.save(commentByTweet);
	return commentMapper.toDto(saved);
    }

    @Override
    public CommentDto getById(long commentId) {
	CommentById entity = commentByIdRepository
			      .findById(commentId)
			      .orElseThrow(() -> newCommentNotFountException(commentId));
	return commentMapper.toDto(entity);
    }

    @Override
    public List<CommentDto> getAll() {
	return commentByIdRepository.findAll().stream()
		   .map(commentMapper::toDto)
		   .toList();
    }


    @Keep
    private static ResourceNotFoundException newCommentNotFountException(long commentId) {
	final String msg = STR."Failed to find tweet' comment by id = \{commentId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 45);
    }

    @Keep
    private static ResourceModifyingException newCommentModifyingException(
	long commentId,
	Throwable cause
    ) {
	final String msg = STR."Failed to modify comment by id =\{commentId} by cause=\{cause.getMessage()}";
	final String front = STR."Failed to modify comment by id = \{commentId}";
	log.warn(msg);
	return new ResourceModifyingException(front, 70);
    }

    @Keep
    private static ResourceModifyingException newCommentAlreadyPresentException(
	Throwable cause
    ) {
	final String msg = STR."Failed to create new comment by cause=\{cause.getMessage()}";
	final String front = "Failed to create new comment";
	log.warn(msg);
	return new ResourceModifyingException(front, 70);
    }
}
