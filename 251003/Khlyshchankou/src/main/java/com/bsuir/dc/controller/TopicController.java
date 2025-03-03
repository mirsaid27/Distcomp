package com.bsuir.dc.controller;

import com.bsuir.dc.dto.request.TopicRequestTo;
import com.bsuir.dc.dto.response.PostResponseTo;
import com.bsuir.dc.dto.response.TopicResponseTo;
import com.bsuir.dc.exception.EntityNotFoundException;
import com.bsuir.dc.service.PostService;
import com.bsuir.dc.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/topics")
public class TopicController {
    private final TopicService topicService;
    private final PostService postService;

    @Autowired
    public TopicController(TopicService topicService, PostService postService) {
        this.topicService = topicService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseTo>> findAll() {
        return new ResponseEntity<>(topicService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        try {
            TopicResponseTo response = topicService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponseTo>> findPostsByTopicId(@PathVariable long id) {
        List<PostResponseTo> posts = postService.findByTopicId(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TopicRequestTo topicRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(topicService.create(topicRequestTo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid TopicRequestTo topicRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            TopicResponseTo updatedTopic = topicService.update(topicRequestTo);
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<?> partialUpdate(@RequestBody @Valid TopicRequestTo topicRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            TopicResponseTo updatedTopic = topicService.partialUpdate(topicRequestTo);
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            topicService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
