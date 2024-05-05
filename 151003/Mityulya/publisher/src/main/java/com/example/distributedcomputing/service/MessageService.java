package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.NoteMapper;
import com.example.distributedcomputing.model.entity.Message;
import com.example.distributedcomputing.model.request.MessageRequestTo;
import com.example.distributedcomputing.model.response.MessageResponseTo;
import com.example.distributedcomputing.repository.MessageRepository;
import com.example.distributedcomputing.repository.TweetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class MessageService {
    private final MessageRepository repository;
    private final NoteMapper mapper;
    private final TweetRepository tweetRepository;

    public Iterable<MessageResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public MessageResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public MessageResponseTo save(@Valid MessageRequestTo entity) {
        if (tweetRepository.findById(entity.getTweetId()).isEmpty()) {
            throw new NotFoundException(404L, "Story with this ID is not found");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public MessageResponseTo update(@Valid MessageRequestTo entity) {
        Long id = entity.getId();
        Message editor = mapper.dtoToEntity(entity);
        editor.setId(id);

        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
