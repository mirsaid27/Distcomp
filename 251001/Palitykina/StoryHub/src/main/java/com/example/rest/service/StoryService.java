package com.example.rest.service;

import com.example.rest.dto.requestDto.StoryRequestTo;
import com.example.rest.dto.responseDto.StoryResponseTo;
import com.example.rest.mapper.StoryMapper;
import com.example.rest.repository.StoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoryService {
    private final StoryRepo storyRepo;
    private final StoryMapper storyMapper;

    public List<StoryResponseTo> getAll() {
        return storyRepo
                .getAll()
                .map(storyMapper::ToStoryResponseTo)
                .toList();
    }
    public StoryResponseTo get(long id) {
        return storyRepo
                .get(id)
                .map(storyMapper::ToStoryResponseTo)
                .orElse(null);
    }
    public StoryResponseTo create(StoryRequestTo input) {
        return storyRepo
                .create(storyMapper.ToStory(input))
                .map(storyMapper::ToStoryResponseTo)
                .orElseThrow();
    }
    public StoryResponseTo update(StoryRequestTo input) {
        return storyRepo
                .update(storyMapper.ToStory(input))
                .map(storyMapper::ToStoryResponseTo)
                .orElseThrow();
    }
    public boolean delete(long id) {
        return storyRepo.delete(id);
    }
}
