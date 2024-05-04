package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.PostRequestTo;
import com.example.publisherservice.dto.responseDto.PostResponseTo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PostService {

    PostResponseTo create(PostRequestTo dto) throws JsonProcessingException;

    List<PostResponseTo> findAllDtos();

    PostResponseTo findDtoById(Long id) throws JsonProcessingException;

    PostResponseTo update(PostRequestTo dto);

    PostResponseTo deletePostById(Long id);
    
}
