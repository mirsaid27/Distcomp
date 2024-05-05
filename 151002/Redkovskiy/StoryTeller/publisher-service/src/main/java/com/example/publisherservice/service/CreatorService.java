package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.CreatorRequestTo;
import com.example.publisherservice.dto.responseDto.CreatorResponseTo;
import com.example.publisherservice.model.Creator;

public interface CreatorService {

    CreatorResponseTo create(CreatorRequestTo dto);

    Iterable<CreatorResponseTo> findAllDtos();

    CreatorResponseTo findDtoById(Long id);

    Creator findCreatorById(Long id);

    CreatorResponseTo update(CreatorRequestTo dto);

    void delete(Long id);
}
