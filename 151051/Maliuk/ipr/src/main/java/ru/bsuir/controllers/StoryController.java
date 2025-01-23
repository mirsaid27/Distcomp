package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.StoryRequestTo;
import ru.bsuir.dto.response.StoryResponseTo;
import ru.bsuir.services.StoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<StoryResponseTo> create(@RequestBody @Valid StoryRequestTo dto) {
        StoryResponseTo response = storyService.createStory(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StoryResponseTo>> getAll() {
        return new ResponseEntity<>(storyService.getAllStory(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseTo> getById(@PathVariable Long id) {
        StoryResponseTo response = storyService.getStoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StoryResponseTo> update(@RequestBody @Valid StoryRequestTo dto) {
        if (dto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StoryResponseTo response = storyService.updateStory(dto.getId(), dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponseTo> updateById(@Valid @PathVariable Long id, @RequestBody @Valid StoryRequestTo dto) {
        StoryResponseTo response = storyService.updateStory(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            storyService.deleteStory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
