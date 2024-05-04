package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.response.ErrorDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.TweetDto;
import by.bsuir.poit.dc.rest.api.dto.response.CommentDto;
import by.bsuir.poit.dc.rest.services.TweetsService;
import by.bsuir.poit.dc.rest.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetsController {
    private final TweetsService tweetsService;
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<TweetDto> publishTweets(
	@RequestBody @Validated(Create.class) UpdateTweetDto dto
    ) {
	var response = tweetsService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getTweets(
	@RequestParam(value = "sticker", required = false) String sticker,
	@RequestParam(value = "editorId", required = false) Long userId,
	@RequestParam(value = "offset", required = false) Long offset,
	@RequestParam(value = "limit", required = false) Integer limit
    ) {
	if (sticker != null && userId != null) {
	    return conflictParameters(HttpStatus.BAD_REQUEST);
	}
	List<TweetDto> tweetsList;
	if (sticker != null) {
	    tweetsList = tweetsService.getTweetBySticker(sticker);
	} else if (userId != null) {
	    tweetsList = tweetsService.getTweetByEditorId(userId);
	} else if (limit != null && offset != null) {
	    tweetsList = tweetsService.getByOffsetAndLimit(offset, limit);
	} else {
	    tweetsList = tweetsService.getAll();
	}
	return ResponseEntity.ok(tweetsList);
    }

    @GetMapping("/{tweetId}")
    public TweetDto getTweetById(
	@PathVariable long tweetId
    ) {
	return tweetsService.getById(tweetId);
    }

    @PutMapping
    public TweetDto updateTweetById(
	@RequestBody @Validated(Update.class) UpdateTweetDto dto
    ) {
	long tweetId = dto.id();
	return tweetsService.update(tweetId, dto);
    }

    @DeleteMapping("/{tweetId}")
    public Object deleteTweetById(
	@PathVariable long tweetId
    ) {
	return tweetsService.delete(tweetId);
    }

    @GetMapping("/{tweetId}/comments")
    public List<CommentDto> getTweetComments(
	@PathVariable long tweetId
    ) {
	return commentService.getAllByTweetId(tweetId);
    }

    @PostMapping("/{tweetId}/comments")
    public void createTweetSticker(
	@PathVariable long tweetId,
	@RequestBody @Validated(Create.class) UpdateTweetStickerDto dto
    ) {
	tweetsService.attachStickerById(tweetId, dto);
    }

    @GetMapping("/{tweetId}/stickers")
    public List<StickerDto> getTweetStickers(
	@PathVariable long tweetId
    ) {
	return tweetsService.getStickersByTweetId(tweetId);
    }

    @DeleteMapping("/{tweetId}/stickers/{stickerId}")
    public Object deleteTweetSticker(
	@PathVariable long tweetId,
	@PathVariable long stickerId
    ) {
	return tweetsService.detachStickerById(tweetId, stickerId);
    }

    private static ResponseEntity<ErrorDto> conflictParameters(
	HttpStatus status
    ) {
	ErrorDto dto = ErrorDto.builder()
			   .errorCode(ErrorDto.codeOf(status, 122))
			   .errorMessage("The provided parameters should be passed separately")
			   .build();
	return ResponseEntity.status(status).body(dto);
    }

}
