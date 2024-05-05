package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.MarkerRequestTo;
import com.example.publisherservice.dto.responseDto.MarkerResponseTo;
import com.example.publisherservice.model.Marker;

public interface MarkerService {

    MarkerResponseTo create(MarkerRequestTo dto);

    Iterable<MarkerResponseTo> findAllDtos();

    MarkerResponseTo findDtoById(Long id);

    Marker findMarkerById(Long id);

    MarkerResponseTo update(MarkerRequestTo dto);

    void delete(Long id);
}
