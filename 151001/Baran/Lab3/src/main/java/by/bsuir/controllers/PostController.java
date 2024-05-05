package by.bsuir.controllers;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseTo>> getPosts(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(postService.getPosts(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseTo> getPost(@PathVariable Long id) {
        return ResponseEntity.status(200).body(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PostResponseTo> savePost(@RequestBody PostRequestTo post) {
        PostResponseTo savedPost = postService.savePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PutMapping()
    public ResponseEntity<PostResponseTo> updatePost(@RequestBody PostRequestTo post) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(post));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<List<PostResponseTo>> getEditorByIssueId(@PathVariable Long id) {
        return ResponseEntity.status(200).body(postService.getPostByIssueId(id));
    }
}
