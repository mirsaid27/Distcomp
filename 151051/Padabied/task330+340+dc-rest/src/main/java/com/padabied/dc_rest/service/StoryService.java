package com.padabied.dc_rest.service;

import com.padabied.dc_rest.mapper.StoryMapper;
import com.padabied.dc_rest.model.Story;
import com.padabied.dc_rest.model.User;
import com.padabied.dc_rest.model.dto.requests.StoryRequestTo;
import com.padabied.dc_rest.model.dto.responses.StoryResponseTo;
import com.padabied.dc_rest.repository.interfaces.StoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    @CacheEvict(value = "stories", allEntries = true)
    public StoryResponseTo createStory(StoryRequestTo storyRequestDto) {
        Story story = storyMapper.toEntity(storyRequestDto);

        User user = new User();
        user.setId(storyRequestDto.getUserId());
        story.setUser(user);

        story = storyRepository.save(story);

        return storyMapper.toResponse(story);
    }

    @Cacheable(value = "stories", key = "#id")
    public StoryResponseTo getStoryById(Long id) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        return storyOptional.map(storyMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Story not found"));
    }

    //@Cacheable(value = "storiesList")
    public List<StoryResponseTo> getAllStories() {
        return storyRepository.findAll().stream()
                .map(storyMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "stories", key = "#id")
    public StoryResponseTo updateStory(Long id, StoryRequestTo storyRequestDto) {
        Story existingStory = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        if (storyRequestDto.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        existingStory.setTitle(storyRequestDto.getTitle());
        existingStory.setContent(storyRequestDto.getContent());

        storyRepository.save(existingStory);
        return storyMapper.toResponse(existingStory);
    }

    @CacheEvict(value = "stories", key = "#id")
    public void deleteStory(Long id) {
        if (!storyRepository.existsById(id)) {
            throw new RuntimeException("Story not found");
        }
        storyRepository.deleteById(id);
    }
}
