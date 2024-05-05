package com.example.distributedcomputing.service;


import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.exceptions.AlreadyExist;
import com.example.distributedcomputing.mapper.StoryMapper;
import com.example.distributedcomputing.model.entity.Tweet;
import com.example.distributedcomputing.model.request.TweetRequestTo;
import com.example.distributedcomputing.model.response.TweetResponseTo;
import com.example.distributedcomputing.repository.TweetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class TweetService {
    private final UserService userService;
    private final TweetRepository repository;
    private final StoryMapper mapper;

    public Iterable<TweetResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public TweetResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public TweetResponseTo save(@Valid TweetRequestTo entity) {
        userService.getById(entity.userId());
        if (repository.findByTitle(entity.title()).isPresent()) {
            throw new AlreadyExist(403L, "Story with this title already exist");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public TweetResponseTo update(@Valid TweetRequestTo entity) {
        Long id = entity.id();
        Tweet editor = mapper.dtoToEntity(entity);
        editor.setId(id);
        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
