package by.bsuir.poit.dc.cassandra.api.controllers;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.CommentDto;
import by.bsuir.poit.dc.cassandra.services.CommentService;
import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
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
@RequestMapping("/api/v1.0/comments")
public class WebCommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createTweetComment(
	@RequestBody @Validated(Create.class) UpdateCommentDto dto) {
	CommentDto response = commentService.save(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
	List<CommentDto> list = commentService.getAll();
	return ResponseEntity.ok(list);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(
	@PathVariable long commentId) {
	return commentService.getById(commentId);
    }

    @PutMapping
    public CommentDto updateCommentById(
	@RequestBody @Validated(Update.class) UpdateCommentDto dto) {
	long commentId = dto.id();
	return commentService.update(commentId, dto);
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
