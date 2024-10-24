package com.padabied.dc_rest.controller;

import com.padabied.dc_rest.model.dto.requests.CommentRequestTo;
import com.padabied.dc_rest.model.dto.responses.CommentResponseTo;
import com.padabied.dc_rest.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<CommentResponseTo> createComment(@RequestBody @Valid CommentRequestTo commentRequestDto) {
        CommentResponseTo createdComment = commentService.createComment(commentRequestDto);

        try {
            restTemplate.postForEntity("http://localhost:24130/api/v1.0/comments", createdComment, CommentResponseTo.class);
        } catch (RestClientException e) {
            System.err.println("Ошибка при отправке комментария во второй модуль: " + e.getMessage());
        }
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

        try{
            restTemplate.put("http://localhost:24130/api/v1.0/comments/" + id, commentRequestDto);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            try {
                restTemplate.delete("http://localhost:24130/api/v1.0/comments/" + id);
            }
            catch (Exception ignored) {}

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}