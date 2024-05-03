package com.example.publisherservice.repository;

import com.example.publisherservice.model.Marker;
import com.example.publisherservice.model.Story;
import com.example.publisherservice.model.StoryMarker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StoryMarkerRepository extends CrudRepository<StoryMarker, Long> {

    Optional<StoryMarker> findStoryMarkerByStoryAndMarker(Story story, Marker marker);

    List<StoryMarker> findAllByMarker(Marker marker);
}
