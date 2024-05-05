package com.example.lab2.Controller;

import com.example.lab2.DTO.PostRequestTo;
import com.example.lab2.DTO.PostResponseTo;
import com.example.lab2.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping(value = "posts")
    public ResponseEntity<?> create(@RequestBody PostRequestTo postRequestTo) {
        PostResponseTo post = postService.create(postRequestTo);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping(value = "posts", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<PostResponseTo> list = postService.readAll(pageInd, numOfElem, sortedBy, direction);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "posts/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        PostResponseTo post = postService.read(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping(value = "posts")
    public ResponseEntity<?> update(@RequestBody PostRequestTo postRequestTo) {
        PostResponseTo post = postService.update(postRequestTo, postRequestTo.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping(value = "posts/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
