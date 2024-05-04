package org.example.discussionservice.rest.controller;

import org.example.discussionservice.dto.PostDto;
import org.example.discussionservice.model.Post;
import org.example.discussionservice.service.PostService;
import org.example.discussionservice.util.NullableProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return NullableProvider.requireNotNull(postService.getPostDtoById(id));
    }

    @GetMapping
    public List<PostDto> getPosts() {
        return NullableProvider.requireNotNull(postService.getAllPostDtos());
    }

    @PostMapping
    public PostDto addPosts(@RequestBody PostDto post) {
        return NullableProvider.requireNotNull(postService.addPost(post));
    }

    @PutMapping
    public PostDto updatePosts(@RequestBody PostDto post) {
        return NullableProvider.requireNotNull(postService.updatePost(post));
    }

    @DeleteMapping("/{id}")
    public PostDto deletePostById(@PathVariable Long id) {
        return NullableProvider.requireNotNull(postService.deletePostById(id));
    }
}
