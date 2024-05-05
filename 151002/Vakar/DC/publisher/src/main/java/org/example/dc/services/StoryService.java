package org.example.dc.services;

import org.example.dc.model.StoryDto;

import java.util.List;

public interface StoryService {
    List<StoryDto> getStorys();
    StoryDto getStoryById(int id);
    StoryDto createStory(StoryDto storyDto);
    boolean delete(int id) throws Exception;
    StoryDto update(StoryDto storyDto);
}
