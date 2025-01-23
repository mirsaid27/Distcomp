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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.bsuir.dto.request.CommentRequestTo;
import ru.bsuir.dto.response.CommentResponseTo;
import ru.bsuir.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<CommentResponseTo> create(@RequestBody @Valid CommentRequestTo commentRequest) {
        CommentResponseTo response = commentService.createComment(commentRequest);
        try {
            restTemplate.postForEntity("http://localhost:24130/api/v1.0/comments", response, CommentResponseTo.class);
        } catch (RestClientException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getAll() {
        return new ResponseEntity<>(commentService.getAllComment(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getById(@PathVariable Long id) {
        return  new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentResponseTo> update(@RequestBody @Valid CommentRequestTo commentRequest) {
        Long id = commentRequest.getId();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CommentResponseTo updatedComment = commentService.updateComment(id, commentRequest);

        try{
            restTemplate.put("http://localhost:24130/api/v1.0/comments/" + id, commentRequest);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseTo> updateCommentWithID(@PathVariable Long id, @RequestBody @Valid CommentRequestTo commentRequest) {
        CommentResponseTo updatedComment = commentService.updateComment(id, commentRequest);
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
