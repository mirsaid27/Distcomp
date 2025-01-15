package ru.bsuir.services;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.StoryRequestTo;
import ru.bsuir.dto.response.StoryResponseTo;
import ru.bsuir.entity.Creator;
import ru.bsuir.entity.Story;
import ru.bsuir.irepositories.IStoryRepository;
import ru.bsuir.mapper.StoryMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class StoryService {

    private final IStoryRepository storyRepository;

    private final StoryMapper storyMapper;

    @CacheEvict(value = "tweets", allEntries = true)
    public StoryResponseTo createStory(StoryRequestTo storyRequest) {

        Story story = storyMapper.toEntity(storyRequest);

        Creator creator = new Creator();
        creator.setId(storyRequest.getEditorId());
        story.setEditor(creator);

        story = storyRepository.save(story);

        return storyMapper.toDTO(story);
    }
    @Cacheable(value = "story", key = "#id")
    public StoryResponseTo getStoryById(Long id) {
        Optional<Story> storyOpt = storyRepository.findById(id);
        return storyOpt.map(storyMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Story not found"));
    }

    public List<StoryResponseTo> getAllStory(){
        return storyRepository.findAll().stream()
                .map(storyMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = "story", key = "#id")
    public StoryResponseTo updateStory(Long id, StoryRequestTo storyRequest) {

        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        if(storyRequest.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        story.setTitle(storyRequest.getTitle());
        story.setContent(storyRequest.getContent());
        storyRepository.save(story);
        return storyMapper.toDTO(story);
    }

    @CacheEvict(value = "story", key = "#id")
    public void deleteStory(Long id) {

        if(!storyRepository.existsById(id)) {
            throw new RuntimeException("Story not found");

        }
        storyRepository.deleteById(id);
    }
}
