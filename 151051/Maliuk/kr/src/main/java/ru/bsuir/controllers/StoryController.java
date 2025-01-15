package ru.bsuir.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.StoryRequestTo;
import ru.bsuir.dto.response.StoryResponseTo;
import ru.bsuir.services.StoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService tweetService) {
        this.storyService = tweetService;
    }

    @PostMapping
    public ResponseEntity<StoryResponseTo> create(@RequestBody @Valid StoryRequestTo dto) {
        StoryResponseTo response = storyService.createStory(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StoryResponseTo>> getAll() {
        return new ResponseEntity<>(storyService.getAllStorys(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseTo> getById(@PathVariable Long id) {
        return storyService.getStoryById(id)
                .map(story -> new ResponseEntity<>(story, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<StoryResponseTo> update(@RequestBody @Valid StoryRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return storyService.updateStory(dto.id(), dto)
                .map(updatedStory -> new ResponseEntity<>(updatedStory, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid StoryRequestTo dto) {
        return storyService.updateStory(id, dto)
                .map(updatedStory -> new ResponseEntity<>(updatedStory, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (storyService.deleteStory(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
