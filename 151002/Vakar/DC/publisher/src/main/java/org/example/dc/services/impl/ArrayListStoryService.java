package org.example.dc.services.impl;

import org.example.dc.model.StoryDto;
import org.example.dc.services.StoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArrayListStoryService implements StoryService {
    private static int id = 1;
    private List<StoryDto> storys = new ArrayList<>();
    @Override
    public List<StoryDto> getStorys() {
        return storys;
    }

    @Override
    public StoryDto getStoryById(int id) {
        return storys.stream()
                .filter(issue -> issue.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public StoryDto createStory(StoryDto storyDto) {
        storyDto.setId(id++);
        storyDto.setCreated(new Date(System.currentTimeMillis()));
        storyDto.setModified(new Date(System.currentTimeMillis()));
        storys.add(storyDto);
        return storyDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        StoryDto story = storys.stream()
                .filter(is -> is.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        storys.remove(story);
        return true;
    }

    @Override
    public StoryDto update(StoryDto storyDto) {
        StoryDto story = storys.stream()
                .filter(issue1 -> issue1.getId() == storyDto.getId())
                .findFirst().get();
        story.setTitle(storyDto.getTitle());
        story.setModified(new Date(System.currentTimeMillis()));
        story.setContent(storyDto.getContent());
        story.setUserId(storyDto.getUserId());
        return story;
    }
}
