package com.example.publisherservice.service;

import com.example.publisherservice.model.Marker;
import com.example.publisherservice.model.Story;
import com.example.publisherservice.model.StoryMarker;

import java.util.Optional;

public interface StoryMarkerService {

    Optional<StoryMarker> findByStoryAndMarker(Story story, Marker marker);

    void save(StoryMarker storyMarker);

    StoryMarker embedEntities(Story story, Marker marker);

    void updateStory(StoryMarker storyMarker, Story story);

    void deleteAllByMarker(Marker marker);
}
