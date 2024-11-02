package com.example.discussion.controller;

import com.example.discussion.model.dto.CommentRequestTo;
import com.example.discussion.model.dto.CommentResponseTo;
import com.example.discussion.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseTo> createComment(@RequestBody @Valid CommentRequestTo commentRequestTo) {
        CommentResponseTo createdComment = commentService.createComment(commentRequestTo);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getAllComments() {
        List<CommentResponseTo> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getCommentById(@PathVariable Long id) {
        CommentResponseTo comment = commentService.getCommentById(id);

        if (comment.isErrorExist()) {
            return new ResponseEntity<>(comment, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseTo> updateComment(@PathVariable Long id, @RequestBody @Valid CommentRequestTo commentRequestDto) {
        CommentResponseTo updatedComment = commentService.updateComment(id, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentResponseTo> updateCommentWithoutId(@RequestBody @Valid CommentRequestTo commentRequestDto) {
        Long id = commentRequestDto.getId();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CommentResponseTo updatedComment = commentService.updateComment(id, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
