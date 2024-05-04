package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.StoryRequestTo;
import com.example.publisherservice.dto.responseDto.StoryResponseTo;
import com.example.publisherservice.model.Story;

public interface StoryService {

    StoryResponseTo create(StoryRequestTo dto);

    Iterable<StoryResponseTo> findAllDtos();

    StoryResponseTo findDtoById(Long id);

    Story findStoryById(Long id);

    StoryResponseTo update(StoryRequestTo dto);

    void delete(Long id);
}
