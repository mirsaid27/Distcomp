package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.EditorAlreadyExist;
import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.EditorMapper;
import com.example.distributedcomputing.model.entity.User;
import com.example.distributedcomputing.model.request.UserRequestTo;
import com.example.distributedcomputing.model.response.UserResponseTo;
import com.example.distributedcomputing.repository.UserRepository;
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
@CacheConfig(cacheNames = "usersCache")
public class UserService {
    private final UserRepository repository;
    private final EditorMapper mapper;

    @Cacheable(cacheNames = "users")
    public Iterable<UserResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }

    @Cacheable(cacheNames = "users", key = "#id", unless = "#result == null")
    public UserResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException(404L, "Editor is not found"));
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserResponseTo save(@Valid UserRequestTo entity) {
        if(repository.findByLogin(entity.login()).isPresent()) {
            throw new EditorAlreadyExist(403L, "Editor with this login is already exist");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserResponseTo update(@Valid UserRequestTo requestTo) {
        Long id = requestTo.id();
        User entity = mapper.dtoToEntity(requestTo);
        entity.setId(id);
        return mapper.entityToDto(repository.save(entity));

    }

    @Caching(evict = { @CacheEvict(cacheNames = "users", key = "#id"),
            @CacheEvict(cacheNames = "users", allEntries = true) })
    public void delete(@Min(0) Long id) {
      repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
      repository.deleteById(id);
    }
}
