package com.example.controllers;

import com.example.api.dto.PostRequestTo;
import com.example.api.dto.PostResponseTo;
import com.example.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {
    @Autowired
    PostService PostService;

    @GetMapping
    public ResponseEntity<List<PostResponseTo>> getPosts() {
        return ResponseEntity.status(200).body(PostService.getPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseTo> getPost(@PathVariable Long id) {
        return ResponseEntity.status(200).body(PostService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        PostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PostResponseTo> savePost(@RequestBody PostRequestTo Post) {
        PostResponseTo savedPost = PostService.savePost(Post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PutMapping()
    public ResponseEntity<PostResponseTo> updatePost(@RequestBody PostRequestTo Post) {
        return ResponseEntity.status(HttpStatus.OK).body(PostService.updatePost(Post));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<PostResponseTo> getEditorByIssueId(@PathVariable Long id){
        return ResponseEntity.status(200).body(PostService.getPostByIssueId(id));
    }
}