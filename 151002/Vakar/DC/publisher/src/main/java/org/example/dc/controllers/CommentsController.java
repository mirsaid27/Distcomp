package org.example.dc.controllers;

import org.example.dc.model.CommentDto;
import org.example.dc.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1.0/comments")
public class CommentsController {
    private Map<Integer, CommentDto> cache = new HashMap<>();

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments() {
        return commentService.getComments();
    }

    @Cacheable
    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable int id) {
        if(!cache.containsKey(id)) {
            cache.put(id, commentService.getCommentById(id));
            return commentService.getCommentById(id);
        } else {
            return cache.get(id);
        }
    }

    @PostMapping
    public CommentDto createComment(@RequestBody @Valid CommentDto comment, BindingResult br, HttpServletResponse response) {
        if(br.hasErrors()) {
            response.setStatus(403);
            return new CommentDto();
        }
        response.setStatus(201);
        try {
            return commentService.create(comment);
        } catch (Exception e) {
            response.setStatus(403);
            return new CommentDto();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            commentService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    @PutMapping
    public CommentDto update(@RequestBody @Valid CommentDto comment, BindingResult br, HttpServletResponse response) {
        cache.remove(comment.getId());
        if(br.hasErrors()) {
            response.setStatus(402);
            return commentService.getCommentById(comment.getId());
        }
        response.setStatus(200);
        return commentService.update(comment);
    }
}
