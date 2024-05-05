package by.bsuir.controllers;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public List<PostResponseTo> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public PostResponseTo getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @PostMapping
    public PostResponseTo savePost(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody PostRequestTo post) {
        return postService.savePost(post, acceptLanguageHeader);
    }

    @PutMapping()
    public PostResponseTo updatePost(@RequestHeader("Accept-Language") String acceptLanguageHeader, @RequestBody PostRequestTo post) {
        return postService.updatePost(post, acceptLanguageHeader);
    }

    @GetMapping("/byIssue/{id}")
    public List<PostResponseTo> getEditorByIssueId(@PathVariable Long id) {
        return postService.getPostByIssueId(id);
    }
}
