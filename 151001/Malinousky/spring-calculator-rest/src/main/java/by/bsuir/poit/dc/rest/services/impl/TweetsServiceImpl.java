package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.StickerMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.TweetStickerMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.TweetMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.CommentMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.TweetDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.TweetStickerRepository;
import by.bsuir.poit.dc.rest.dao.TweetRepository;
import by.bsuir.poit.dc.rest.dao.EditorRepository;
import by.bsuir.poit.dc.rest.model.Tweet;
import by.bsuir.poit.dc.rest.model.TweetSticker;
import by.bsuir.poit.dc.rest.model.TweetStickerId;
import by.bsuir.poit.dc.rest.services.TweetsService;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@CacheConfig(cacheNames = "tweetsCache")
@RequiredArgsConstructor
public class TweetsServiceImpl implements TweetsService {
    private final EditorRepository editorRepository;
    private final TweetStickerRepository tweetStickerRepository;
    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;
    private final CommentMapper commentMapper;
    private final StickerMapper stickerMapper;
    private final TweetStickerMapper tweetStickerMapper;

    @Override
    @CatchThrows(call = "newTweetCreationException")
    public TweetDto create(UpdateTweetDto dto) {
	Tweet entity = tweetMapper.toEntity(dto);
	Tweet savedEntity = tweetRepository.save(entity);
	return tweetMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newTweetModifyingException",
	args = "tweetId")
    @Cacheable(key = "#tweetId")
    public TweetDto update(long tweetId, UpdateTweetDto dto) {
	Tweet entity = tweetRepository
			  .findById(tweetId)
			  .orElseThrow(() -> newTweetNotFoundException(tweetId));
	Tweet updatedEntity = tweetMapper.partialUpdate(entity, dto);
	Tweet savedEntity = tweetRepository.save(updatedEntity);
	return tweetMapper.toDto(savedEntity);
    }

    @Override
    @Cacheable
    public TweetDto getById(long tweetsId) {
	return tweetRepository
		   .findById(tweetsId)
		   .map(tweetMapper::toDto)
		   .orElseThrow(() -> newTweetNotFoundException(tweetsId));
    }

    @Override
    public List<TweetDto> getByOffsetAndLimit(long offset, int limit) {
	int number = (int) (offset / limit);
	PageRequest pageRequest = PageRequest.of(number, limit);
	Page<Tweet> pages = tweetRepository.findAll(pageRequest);
	return pages.stream()
		   .map(tweetMapper::toDto)
		   .toList();
    }

    @Override
    public List<TweetDto> getAll() {
	return tweetRepository.findAll().stream()
		   .map(tweetMapper::toDto)
		   .toList();
    }

    @Override
    public PresenceDto existsById(long tweetId) {
	return PresenceDto.wrap(tweetRepository.existsById(tweetId));
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newTweetModifyingException",
	args = "tweetId")
    public PresenceDto delete(long tweetId) {
	return PresenceDto
		   .wrap(tweetRepository.existsById(tweetId))
		   .ifPresent(() -> tweetRepository.deleteById(tweetId));
    }


    @Override
    @Transactional
    @CatchThrows(
	call = "newTweetModifyingException",
	args = "tweetId")
    public void attachStickerById(long tweetId, UpdateTweetStickerDto dto) {
	if (!tweetRepository.existsById(tweetId)) {
	    throw newTweetNotFoundException(tweetId);
	}
	TweetSticker entity = tweetStickerMapper.toEntity(tweetId, dto);
	if (tweetStickerRepository.existsById(entity.getId())) {
	    throw newStickerTweetAlreadyPresent(entity.getId());
	}
	TweetSticker _ = tweetStickerRepository.save(entity);
    }

    @Override
    @Transactional
    public PresenceDto detachStickerById(long tweetId, long stickerId) {
	return PresenceDto
		   .wrap(tweetStickerRepository.existsByTweetIdAndStickerId(tweetId, stickerId))
		   .ifPresent(() -> tweetStickerRepository.deleteByTweetIdAndStickerId(tweetId, stickerId));
    }

    @Override
    @Transactional
    public List<TweetDto> getTweetByEditorId(long editorId) {
	if (!editorRepository.existsById(editorId)) {
	    throw newUserNotFoundException(editorId);
	}
	return tweetMapper.toDtoList(
	    tweetRepository.findAllByEditorId(editorId)
	);
    }

    @Override
    @Cacheable
    public List<TweetDto> getTweetBySticker(String sticker) {
	List<TweetSticker> tweet = tweetStickerRepository.findAllByStickerName(sticker);
	return tweet.stream()
		   .map(TweetSticker::getTweet)
		   .map(tweetMapper::toDto)
		   .toList();
    }

    @Override
    public List<StickerDto> getStickersByTweetId(long tweetId) {
	Tweet tweet = tweetRepository
			.findWithStickersById(tweetId)
			.orElseThrow(() -> newTweetNotFoundException(tweetId));
	return tweet.getStickers().stream()
		   .map(TweetSticker::getSticker)
		   .map(stickerMapper::toDto)
		   .toList();
    }

    @Keep
    private static ResourceModifyingException newTweetCreationException(
	Throwable cause
    ) {
	final String msg = STR."Failed to create tweet by cause = \{cause.getMessage()}";
	final String frontMsg = "Failed to create tweet. Check that tweet' title should be unique";
	log.warn(msg);
	return new ResourceModifyingException(frontMsg, 72);
    }

    @Keep
    private static ResourceModifyingException newTweetModifyingException(
	long tweetId,
	Throwable cause
    ) {
	final String msg = STR."Failed to update tweet by id = \{tweetId} by cause = \{cause.getMessage()}";
	final String frontMsg = "Failed to change tweet content. Verify that dto doesn't violate restrictions";
	log.warn(msg);
	return new ResourceModifyingException(frontMsg, 73);

    }

    @Keep
    private static ResourceModifyingException newCommentCreationException(
	long tweetId,
	Throwable cause
    ) {
	final String msg = STR."Failed to create new comment for tweet entity with id = \{tweetId} by cause = \{cause.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(msg, 71);
    }

    @Keep
    private static ResourceNotFoundException newTweetNotFoundException(long tweetId) {
	final String msg = STR."Failed to find tweet by id = \{tweetId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 44);
    }

    @Keep
    private static ResourceNotFoundException newLabelNotFoundException(long labelId) {
	final String msg = STR."Failed to find sticker by id = \{labelId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 55);

    }

    @Keep
    private static ResourceBusyException newStickerTweetAlreadyPresent(TweetStickerId id) {
	final String msg = STR."The tweet sticker is already present by tweetId=\{id.getLabelId()} and labelId=\{id.getLabelId()}";
	log.warn(msg);
	return new ResourceBusyException(msg, 52);

    }

    @Keep
    private static ResourceNotFoundException newUserNotFoundException(long userId) {
	final String msg = STR."Failed to find tweet for editor with id = \{userId}, because editor is not exists";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 43);
    }
}
