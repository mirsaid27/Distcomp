package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.CreatorRequestTo;
import com.example.publisherservice.dto.responseDto.CreatorResponseTo;
import com.example.publisherservice.model.Creator;
import com.example.publisherservice.repository.CreatorRepository;
import com.example.publisherservice.util.exceptions.DataDuplicationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepository creatorRepository;

    @Autowired
    public CreatorServiceImpl(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    @Override
    public CreatorResponseTo create(CreatorRequestTo dto) {

        creatorRepository.findByLogin(dto.getLogin()).ifPresent(login -> {
            throw new DataDuplicationException("Creator with login: \"" + login + "\" already exists");
        });

        Creator creator = new Creator();
        requestDtoToCreator(creator, dto);
        creatorRepository.save(creator);
        return creatorToResponseDto(creator);
    }

    @Override
    public Iterable<CreatorResponseTo> findAllDtos() {
        return StreamSupport.stream(creatorRepository.findAll().spliterator(), false)
                .map(this::creatorToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "creator", key = "#id", unless = "#result==null")
    public CreatorResponseTo findDtoById(Long id) {
        return creatorToResponseDto(findCreatorById(id));
    }

    @Override
    public Creator findCreatorById(Long id) {
        return creatorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Creator with id " + id + " can't be found"));
    }

    @Override
    @CachePut(value = "creator", key = "#dto.id", unless="#result==null")
    public CreatorResponseTo update(CreatorRequestTo dto) {
        creatorRepository.findByLogin(dto.getLogin()).ifPresent(login -> {
            throw new DataDuplicationException("Creator with login: \"" + login + "\" already exists");
        });

        Creator creator = findCreatorById(dto.getId());
        requestDtoToCreator(creator, dto);
        creatorRepository.save(creator);
        return creatorToResponseDto(creator);
    }

    @Override
    @CacheEvict(cacheNames = "creator", key = "#id")
    public void delete(Long id) {
        Creator creator = findCreatorById(id);
        creatorRepository.delete(creator);
    }

    void requestDtoToCreator(Creator creator, CreatorRequestTo dto) {
        creator.setLogin(dto.getLogin());
        creator.setPassword(dto.getPassword());
        creator.setFirstName(dto.getFirstname());
        creator.setLastName(dto.getLastname());
    }

    CreatorResponseTo creatorToResponseDto(Creator creator) {
        CreatorResponseTo dto = new CreatorResponseTo();
        dto.setId(creator.getId());
        dto.setLogin(creator.getLogin());
        dto.setPassword(creator.getPassword());
        dto.setFirstname(creator.getFirstName());
        dto.setLastname(creator.getLastName());
        return dto;
    }
}
