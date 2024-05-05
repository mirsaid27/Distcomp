package com.example.publisherservice.rest.controller;

import com.example.publisherservice.dto.requestDto.PostRequestTo;
import com.example.publisherservice.dto.responseDto.PostResponseTo;
import com.example.publisherservice.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public List<PostResponseTo> getTasks() {
        return postService.findAllDtos();
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo getTask(@PathVariable final Long id) throws JsonProcessingException {
        return postService.findDtoById(id);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseTo addTask(@Valid @RequestBody PostRequestTo taskDto) throws JsonProcessingException {
        return postService.create(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo updateTask(@Valid @RequestBody final PostRequestTo taskDto) {
        return postService.update(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable final Long id) {
        postService.deletePostById(id);
    }
}
