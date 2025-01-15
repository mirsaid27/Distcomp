package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bsuir.dto.request.CCommentRequestTo;
import ru.bsuir.dto.response.CCommentResponseTo;
import ru.bsuir.services.CCommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CCommentController {


    CCommentService commentService;

    @PostMapping
    public ResponseEntity<CCommentResponseTo> create(@RequestBody @Valid CCommentRequestTo commentRequest) {
        CCommentResponseTo response = commentService.createComment(commentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CCommentResponseTo>> getAll() {
        return new ResponseEntity<>(commentService.getAllComment(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CCommentResponseTo> getById(@PathVariable Long id) {
        CCommentResponseTo comment = commentService.getCommentById(id);

        if (comment.isErrorExist()) {
            return new ResponseEntity<>(comment, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CCommentResponseTo> update(@RequestBody @Valid CCommentRequestTo commentRequest) {
        if (commentRequest.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CCommentResponseTo response = commentService.updateComment(commentRequest.getId(), commentRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CCommentResponseTo> updateWithId(@PathVariable Long id, @RequestBody @Valid CCommentRequestTo commentRequest) {
        CCommentResponseTo response = commentService.updateComment(id, commentRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
