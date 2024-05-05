package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.EditorAlreadyExist;
import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.CreatorMapper;
import com.example.distributedcomputing.model.entity.Creator;
import com.example.distributedcomputing.model.request.CreatorRequestTo;
import com.example.distributedcomputing.model.response.CreatorResponseTo;
import com.example.distributedcomputing.repository.CreatorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@CacheConfig(cacheNames = "creatorsCache")
public class CreatorService {
    private final CreatorRepository repository;
    private final CreatorMapper mapper;

    @Cacheable(cacheNames = "creators")
    public Iterable<CreatorResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }

    @Cacheable(cacheNames = "creators", key = "#id", unless = "#result == null")
    public CreatorResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException(404L, "Editor is not found"));
    }

    @CacheEvict(cacheNames = "creators", allEntries = true)
    public CreatorResponseTo save(@Valid CreatorRequestTo entity) {
        if(repository.findByLogin(entity.login()).isPresent()) {
            throw new EditorAlreadyExist(403L, "Editor with this login is already exist");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }

    @CacheEvict(cacheNames = "creators", allEntries = true)
    public CreatorResponseTo update(@Valid CreatorRequestTo requestTo) {
        Long id = requestTo.id();
        Creator entity = mapper.dtoToEntity(requestTo);
        entity.setId(id);
        return mapper.entityToDto(repository.save(entity));

    }

    @Caching(evict = { @CacheEvict(cacheNames = "creators", key = "#id"),
            @CacheEvict(cacheNames = "creators", allEntries = true) })
    public void delete(@Min(0) Long id) {
      repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
      repository.deleteById(id);
    }
}
