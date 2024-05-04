package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.rest.api.dto.response.CommentDto;
import by.bsuir.poit.dc.rest.services.CommentService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/v1.0/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @Deprecated
    public ResponseEntity<List<CommentDto>> getAllComments() {
	return ResponseEntity.ok(commentService.getAll());
    }

    @PostMapping
    public ResponseEntity<CommentDto> createTweetComment(
	@RequestBody @Validated(Create.class) UpdateCommentDto dto,
	@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) @Nullable String language) {
	long tweetId = dto.tweetId();
	CommentDto response = commentService.save(dto, tweetId, language);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(
	@PathVariable long commentId) {
	return commentService.getById(commentId);
    }

    @PutMapping
    public CommentDto updateCommentById(
	@RequestBody @Validated(Update.class) UpdateCommentDto dto,
	@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) String language) {
	long commentId = dto.id();
	return commentService.update(commentId, dto, language);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteCommentById(
	@PathVariable long commentId) {
	var status = commentService.delete(commentId).isPresent()
			 ? HttpStatus.NO_CONTENT
			 : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }
}
