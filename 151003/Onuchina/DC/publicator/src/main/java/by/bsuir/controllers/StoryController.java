package by.bsuir.controllers;

import by.bsuir.dto.StoryRequestTo;
import by.bsuir.dto.StoryResponseTo;
import by.bsuir.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/storys")
public class StoryController {
    @Autowired
    StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryResponseTo>> getStories(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(storyService.getStories(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseTo> getStory(@PathVariable int id) {
        return ResponseEntity.status(200).body(storyService.getStoryById((long) id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<StoryResponseTo> saveStory(@RequestBody StoryRequestTo story) {
        StoryResponseTo savedStory = storyService.saveStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStory);
    }

    @PutMapping()
    public ResponseEntity<StoryResponseTo> updateStory(@RequestBody StoryRequestTo story) {
        return ResponseEntity.status(HttpStatus.OK).body(storyService.updateStory(story));
    }

    @GetMapping("/byCriteria")
    public ResponseEntity<List<StoryResponseTo>> getStoryByCriteria(
            @RequestParam(required = false) List<String> tagName,
            @RequestParam(required = false) List<Long> tagId,
            @RequestParam(required = false) String authorLogin,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content){
        return ResponseEntity.status(HttpStatus.OK).body(storyService.getStoryByCriteria(tagName, tagId, authorLogin, title, content));
    }
}