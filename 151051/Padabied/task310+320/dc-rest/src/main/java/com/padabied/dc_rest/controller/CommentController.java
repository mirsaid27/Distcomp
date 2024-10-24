package com.padabied.dc_rest.controller;

import com.padabied.dc_rest.model.dto.requests.CommentRequestTo;
import com.padabied.dc_rest.model.dto.responses.CommentResponseTo;
import com.padabied.dc_rest.service.CommentService;
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

    // Создать новый комментарий
    @PostMapping
    public ResponseEntity<CommentResponseTo> createComment(@RequestBody @Valid CommentRequestTo commentRequestDto) {
        CommentResponseTo createdComment = commentService.createComment(commentRequestDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Получить все комментарии
    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getAllComments() {
        List<CommentResponseTo> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Получить комментарий по id
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getCommentById(@PathVariable Long id) {
        CommentResponseTo comment = commentService.getCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // Обновить комментарий по id
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseTo> updateComment(@PathVariable Long id, @RequestBody @Valid CommentRequestTo commentRequestDto) {
        CommentResponseTo updatedComment = commentService.updateComment(id, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentResponseTo> updateCommentWithoutId(@RequestBody @Valid CommentRequestTo commentRequestDto) {
        Long id = commentRequestDto.getId(); // Получаем id из запроса
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Возвращаем 400 если id отсутствует
        }

        CommentResponseTo updatedComment = commentService.updateComment(id, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // Удалить комментарий по id
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