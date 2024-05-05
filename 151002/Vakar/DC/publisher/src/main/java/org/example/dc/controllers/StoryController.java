package org.example.dc.controllers;

import org.example.dc.model.StoryDto;
import org.example.dc.services.StoryService;
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
@RequestMapping("/v1.0/storys")
public class StoryController {
    private Map<Integer, StoryDto> cache = new HashMap<>();

    @Autowired
    private StoryService storyService;
    @Cacheable
    @GetMapping
    public List<StoryDto> getStorys() {
        return storyService.getStorys();
    }

    @Cacheable
    @GetMapping("/{id}")
    public StoryDto getStory(@PathVariable int id, HttpServletResponse response) {
        StoryDto story = null;
        try {
            response.setStatus(200);
            story = storyService.getStoryById(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
        cache.put(id, story);
        return story;
    }

    @PostMapping
    public StoryDto create(@RequestBody @Valid StoryDto storyDto, BindingResult br, HttpServletResponse response) {
        if (br.hasErrors()) {
            response.setStatus(400);
            return new StoryDto();
        }
        response.setStatus(201);
        try {
            return storyService.createStory(storyDto);
        } catch (Exception e) {
            response.setStatus(403);
            return storyService.getStorys().stream()
                    .filter(i -> i.getTitle().equals(storyDto.getTitle()))
                    .findFirst().orElse(new StoryDto());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            storyService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    @PutMapping
    public StoryDto update(@RequestBody @Valid StoryDto storyDto, BindingResult br, HttpServletResponse response) {
        if (br.hasErrors()) {
           response.setStatus(402);
           return storyService.getStoryById(storyDto.getId());
        }
        return storyService.update(storyDto);
    }
}
