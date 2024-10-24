package com.padabied.dc_rest.controller;

import com.padabied.dc_rest.model.dto.requests.StoryRequestTo;
import com.padabied.dc_rest.model.dto.responses.StoryResponseTo;
import com.padabied.dc_rest.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/storys")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<StoryResponseTo> createStory(@RequestBody @Valid StoryRequestTo storyRequestDto) {
        StoryResponseTo storyResponseDto = storyService.createStory(storyRequestDto);
        return new ResponseEntity<>(storyResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseTo> getStoryById(@PathVariable Long id) {
        StoryResponseTo storyResponseDto = storyService.getStoryById(id);
        return new ResponseEntity<>(storyResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StoryResponseTo>> getAllStories() {
        List<StoryResponseTo> storyResponseDtos = storyService.getAllStories();
        return new ResponseEntity<>(storyResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponseTo> updateStory(
            @Valid
            @PathVariable Long id,
            @RequestBody StoryRequestTo storyRequestDto) {
        StoryResponseTo updatedStoryResponseDto = storyService.updateStory(id, storyRequestDto);
        return new ResponseEntity<>(updatedStoryResponseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StoryResponseTo> updateStory(@RequestBody @Valid StoryRequestTo storyRequestDto) {
        if (storyRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        StoryResponseTo updatedStoryResponseDto = storyService.updateStory(storyRequestDto.getId(), storyRequestDto);
        return new ResponseEntity<>(updatedStoryResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        try {
            storyService.deleteStory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
