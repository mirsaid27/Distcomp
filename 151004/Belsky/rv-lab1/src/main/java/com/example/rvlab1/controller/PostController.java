package com.example.rvlab1.controller;

import com.example.rvlab1.mapper.PostMapper;
import com.example.rvlab1.model.dto.request.PostRequestTo;
import com.example.rvlab1.model.dto.request.PostRequestWithIdTo;
import com.example.rvlab1.model.dto.response.PostResponseTo;
import com.example.rvlab1.model.service.Post;
import com.example.rvlab1.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponseTo>> getAllPosts() {
        return ResponseEntity.ok(postService.getAll().stream().map(postMapper::mapToResponseTo).toList());
    }

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseTo> createPost(@RequestBody PostRequestTo postRequestTo) {
        Post post = postService.createPost(postMapper.mapToBO(postRequestTo));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.mapToResponseTo(post));
    }

    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable("postId") Long postId) {
        Post post = postService.findById(postId);
        postService.deletePost(post);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseTo> getPostById(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        return ResponseEntity.ok(postMapper.mapToResponseTo(post));
    }

    @PutMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseTo> updatePostById(@RequestBody PostRequestWithIdTo postRequestTo) {
        Post post = postService.findById(postRequestTo.getId());
        postMapper.updatePostRequestToToPostBo(postRequestTo, post);
        post = postService.updatePost(post);
        return ResponseEntity.ok(postMapper.mapToResponseTo(post));
    }
}
