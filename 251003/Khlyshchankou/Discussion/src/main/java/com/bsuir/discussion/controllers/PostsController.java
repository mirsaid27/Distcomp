package com.bsuir.discussion.controllers;

import com.bsuir.discussion.dto.requests.PostRequestDTO;
import com.bsuir.discussion.dto.responses.PostResponseDTO;
import com.bsuir.discussion.services.PostsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private final PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDTO createPost(@RequestBody @Valid PostRequestDTO postRequestDTO, BindingResult binding) {
        return postsService.save(postRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> getAllPosts() {
        return postsService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDTO getPostById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable long id){
        postsService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDTO updatePost(@RequestBody @Valid PostRequestDTO postRequestDTO){
        return postsService.update(postRequestDTO);
    }
}
