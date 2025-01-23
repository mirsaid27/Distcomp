package com.example.controller;

import com.example.request.TagRequestTo;
import com.example.response.TagResponseTo;
import com.example.service.TagService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags")
@Data
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagResponseTo findById(@Valid @PathVariable("id") Long id) {
        return tagService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseTo> findAll() {
        return tagService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponseTo create(@Valid @RequestBody TagRequestTo request) {
        return tagService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TagResponseTo update(@Valid @RequestBody TagRequestTo request) {
        return tagService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean removeById(@Valid @PathVariable("id") Long id) {
        return tagService.removeById(id);
    }
}