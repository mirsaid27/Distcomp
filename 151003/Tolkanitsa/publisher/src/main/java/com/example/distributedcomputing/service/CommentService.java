package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.CommentMapper;
import com.example.distributedcomputing.model.entity.Comment;
import com.example.distributedcomputing.model.request.CommentRequestTo;
import com.example.distributedcomputing.model.response.CommentResponseTo;
import com.example.distributedcomputing.repository.CommentRepository;
import com.example.distributedcomputing.repository.IssueRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class CommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final IssueRepository issueRepository;

    public Iterable<CommentResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public CommentResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public CommentResponseTo save(@Valid CommentRequestTo entity) {
        if (issueRepository.findById(entity.getIssueId()).isEmpty()) {
            throw new NotFoundException(404L, "Story with this ID is not found");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public CommentResponseTo update(@Valid CommentRequestTo entity) {
        Long id = entity.getId();
        Comment editor = mapper.dtoToEntity(entity);
        editor.setId(id);

        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
