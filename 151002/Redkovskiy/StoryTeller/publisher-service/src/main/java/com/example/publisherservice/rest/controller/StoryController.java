package com.example.publisherservice.rest.controller;

import com.example.publisherservice.dto.requestDto.StoryRequestTo;
import com.example.publisherservice.dto.responseDto.StoryResponseTo;
import com.example.publisherservice.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/storys")
public class StoryController {

    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("")
    public Iterable<StoryResponseTo> getTasks() {
        return storyService.findAllDtos();
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo getTask(@PathVariable final Long id) {
        return storyService.findDtoById(id);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public StoryResponseTo addTask(@Valid @RequestBody StoryRequestTo taskDto) {
        return storyService.create(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo updateTask(@Valid @RequestBody final StoryRequestTo taskDto) {
        return storyService.update(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable final Long id) {
        storyService.delete(id);
    }
}
