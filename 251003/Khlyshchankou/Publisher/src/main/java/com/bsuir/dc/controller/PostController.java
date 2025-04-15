package com.bsuir.dc.controller;

import com.bsuir.dc.dto.request.PostRequestTo;
import com.bsuir.dc.dto.response.PostResponseTo;
import com.bsuir.dc.util.exception.ValidationException;
import com.bsuir.dc.util.validator.PostValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {
    private final RestClient restClient;
    private final PostValidator postValidator;
    @Autowired
    public PostController(RestClient restClient, PostValidator postValidator) {
        this.restClient = restClient;
        this.postValidator = postValidator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseTo createPost(@RequestBody @Valid PostRequestTo postRequestTo, BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()){
            postValidator.validate(postRequestTo, bindingResult);
        }
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult);

        return restClient.post()
                .uri("/posts")
                .body(postRequestTo)
                .retrieve()
                .body(PostResponseTo.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseTo> getAllPosts() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<List<PostResponseTo>>() {});
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo getPostById(@PathVariable Long id) {
        return restClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .body(PostResponseTo.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable long id) {
        restClient.delete()
                .uri("/posts/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo updatePost(@RequestBody @Valid PostRequestTo postRequestTo) {
        return restClient.put()
                .uri("/posts")
                .body(postRequestTo)
                .retrieve()
                .body(PostResponseTo.class);
    }
}
