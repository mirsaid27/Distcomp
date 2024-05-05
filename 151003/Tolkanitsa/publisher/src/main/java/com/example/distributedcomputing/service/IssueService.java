package com.example.distributedcomputing.service;


import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.exceptions.AlreadyExist;
import com.example.distributedcomputing.mapper.IssueMapper;
import com.example.distributedcomputing.model.entity.Issue;
import com.example.distributedcomputing.model.request.IssueRequestTo;
import com.example.distributedcomputing.model.response.IssueResponseTo;
import com.example.distributedcomputing.repository.IssueRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class IssueService {
    private final CreatorService creatorService;
    private final IssueRepository repository;
    private final IssueMapper mapper;

    public Iterable<IssueResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public IssueResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public IssueResponseTo save(@Valid IssueRequestTo entity) {
        creatorService.getById(entity.creatorId());

        if (repository.findByTitle(entity.title()).isPresent()) {
            throw new AlreadyExist(403L, "Story with this title already exist");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public IssueResponseTo update(@Valid IssueRequestTo entity) {
        Long id = entity.id();
        Issue editor = mapper.dtoToEntity(entity);
        editor.setId(id);
        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
