package com.example.publisherservice.service;

import com.example.publisherservice.model.Marker;
import com.example.publisherservice.model.Story;
import com.example.publisherservice.model.StoryMarker;
import com.example.publisherservice.repository.StoryMarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoryMarkerServiceImpl implements StoryMarkerService {

    private final StoryMarkerRepository storyMarkerRepository;

    @Autowired
    public StoryMarkerServiceImpl(StoryMarkerRepository storyMarkerRepository) {
        this.storyMarkerRepository = storyMarkerRepository;
    }

    @Override
    public Optional<StoryMarker> findByStoryAndMarker(Story story, Marker marker) {
        return storyMarkerRepository.findStoryMarkerByStoryAndMarker(story, marker);
    }

    @Override
    public void save(StoryMarker storyMarker) {
        storyMarkerRepository.save(storyMarker);
    }

    @Override
    public StoryMarker embedEntities(Story story, Marker marker) {
        return new StoryMarker(story, marker);
    }

    @Override
    public void updateStory(StoryMarker storyMarker, Story story) {
        storyMarker.setStory(story);
        save(storyMarker);
    }

    @Override
    public void deleteAllByMarker(Marker marker) {
        storyMarkerRepository.deleteAll(storyMarkerRepository.findAllByMarker(marker));
    }
}
