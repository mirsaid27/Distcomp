package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.MarkerRequestTo;
import com.example.publisherservice.dto.responseDto.MarkerResponseTo;
import com.example.publisherservice.model.Marker;
import com.example.publisherservice.model.Story;
import com.example.publisherservice.repository.MarkerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MarkerServiceImpl implements MarkerService {

    private final MarkerRepository markerRepository;

    private final StoryService storyService;

    private final StoryMarkerService storyMarkerService;

    @Autowired
    public MarkerServiceImpl(MarkerRepository markerRepository,
                             StoryService storyService,
                             StoryMarkerService storyMarkerService) {
        this.markerRepository = markerRepository;
        this.storyService = storyService;
        this.storyMarkerService = storyMarkerService;
    }

    @Override
    public MarkerResponseTo create(MarkerRequestTo dto) {
        Marker marker = new Marker();
        requestDtoToMarker(marker, dto);
        markerRepository.save(marker);

        if(dto.getStoryId() != null) {
            saveEmbeddedEntitiesInJoinTables(storyService.findStoryById(dto.getStoryId()), marker);
        }
        return markerToResponseDto(marker);
    }

    @Override
    public Iterable<MarkerResponseTo> findAllDtos() {
        return StreamSupport.stream(markerRepository.findAll().spliterator(), false)
                .map(this::markerToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "marker", key = "#id", unless = "#result==null")
    public MarkerResponseTo findDtoById(Long id) {
        return markerToResponseDto(findMarkerById(id));
    }

    @Override
    public Marker findMarkerById(Long id) {
        return markerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Marker with id " + id + " can't be found"));
    }

    @Override
    @CachePut(value = "marker", key = "#dto.id", unless="#result==null")
    public MarkerResponseTo update(MarkerRequestTo dto) {
        Marker marker = findMarkerById(dto.getId());
        requestDtoToMarker(marker, dto);
        markerRepository.save(marker);

        /*
        Incomplete logic due to lack of information about subject area.
        To update story-marker join table correctly, front-end should make a request
        to a StoryMarkerController (to know the marked story that should be changed)
        But technical task and tests don't provide any information on how to update
        story-marker table in this case, so creating new join table row
        on every update was provided as a solution
        */
        if(dto.getStoryId() != null) {
            saveEmbeddedEntitiesInJoinTablesIfUnique(storyService.findStoryById(dto.getStoryId()), marker);
        }
        return markerToResponseDto(marker);
    }

    @Override
    @CacheEvict(cacheNames = "marker", key = "#id")
    public void delete(Long id) {
        Marker marker = findMarkerById(id);
        //firstly, delete all referenced story-marker rows in join table
        storyMarkerService.deleteAllByMarker(marker);
        //then delete marker itself
        markerRepository.delete(marker);
    }

    //temporary method due to reasons described in update method
    private void saveEmbeddedEntitiesInJoinTables(Story story, Marker marker) {
        //save story-marker in join table
        storyMarkerService.save(storyMarkerService.embedEntities(story, marker));
    }

    private void saveEmbeddedEntitiesInJoinTablesIfUnique(Story story, Marker marker) {
        if (storyMarkerService.findByStoryAndMarker(story, marker).isEmpty()) {
            saveEmbeddedEntitiesInJoinTables(story, marker);
        }
    }

    private void requestDtoToMarker(Marker marker, MarkerRequestTo dto) {
        marker.setName(dto.getName());
    }

    private MarkerResponseTo markerToResponseDto(Marker marker) {
        MarkerResponseTo dto = new MarkerResponseTo();
        dto.setId(marker.getId());
        dto.setName(marker.getName());
        return dto;
    }
}
