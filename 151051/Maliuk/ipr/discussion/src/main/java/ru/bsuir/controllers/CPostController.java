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
import ru.bsuir.dto.request.CPostRequestTo;
import ru.bsuir.dto.response.CPostResponseTo;
import ru.bsuir.services.CPostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
@RequiredArgsConstructor
public class CPostController {


    CPostService postService;

    @PostMapping
    public ResponseEntity<CPostResponseTo> create(@RequestBody @Valid CPostRequestTo postRequest) {
        CPostResponseTo response = postService.createPost(postRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CPostResponseTo>> getAll() {
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CPostResponseTo> getById(@PathVariable Long id) {
        CPostResponseTo post = postService.getPostById(id);

        if (post.isErrorExist()) {
            return new ResponseEntity<>(post, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CPostResponseTo> update(@RequestBody @Valid CPostRequestTo postRequest) {
        if (postRequest.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CPostResponseTo response = postService.updateComment(postRequest.getId(), postRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CPostResponseTo> updateWithId(@PathVariable Long id, @RequestBody @Valid CPostRequestTo postRequest) {
        CPostResponseTo response = postService.updatePost(id, postRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
