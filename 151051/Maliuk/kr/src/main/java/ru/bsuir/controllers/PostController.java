package ru.bsuir.controllers;

import jakarta.validation.Valid;
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
import ru.bsuir.dto.request.PostRequestTo;
import ru.bsuir.dto.response.PostResponseTo;
import ru.bsuir.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
public class PostController {


    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseTo> create(@RequestBody @Valid PostRequestTo dto) {
        PostResponseTo response = postService.createPost(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseTo>> getAll() {
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseTo> getById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<PostResponseTo> update(@RequestBody @Valid PostRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return postService.updatePost(dto.id(), dto)
                .map(updatedCreator -> new ResponseEntity<>(updatedCreator, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseTo> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequestTo dto) {
        return postService.updatePost(id, dto)
                .map(updatedPost -> new ResponseEntity<>(updatedPost, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.deletePost(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
