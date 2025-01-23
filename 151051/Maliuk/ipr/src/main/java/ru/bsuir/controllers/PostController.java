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
import ru.bsuir.dto.request.PostRequestTo;
import ru.bsuir.dto.response.PostResponseTo;
import ru.bsuir.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<PostResponseTo> create(@RequestBody @Valid PostRequestTo postRequest) {
        PostResponseTo response = postService.createPost(postRequest);
        try {
            restTemplate.postForEntity("http://localhost:24130/api/v1.0/posts", response, PostResponseTo.class);
        } catch (RestClientException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseTo>> getAll() {
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseTo> getById(@PathVariable Long id) {
        return  new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PostResponseTo> update(@RequestBody @Valid PostRequestTo postRequest) {
        Long id = postRequest.getId();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PostResponseTo updatedPost = postService.updatePost(id, postRequest);

        try{
            restTemplate.put("http://localhost:24130/api/v1.0/posts/" + id, postRequest);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseTo> updatePostWithID(@PathVariable Long id, @RequestBody @Valid PostRequestTo postRequest) {
        PostResponseTo updatedPost = postService.updatePost(id, postRequest);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            try {
                restTemplate.delete("http://localhost:24130/api/v1.0/posts/" + id);
            }
            catch (Exception ignored) {}

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
