package by.bsuir.test_rw.service.db_interaction.implementations;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.entity.implementations.Issue;
import by.bsuir.test_rw.repository.interfaces.IssueRepository;
import by.bsuir.test_rw.service.db_interaction.interfaces.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomIssueService implements IssueService {

    private final IssueRepository issueRepository;

    @Override
    @Cacheable(value = "issue", key = "#id")
    public Issue findById(Long id) throws EntityNotFoundException {
        return issueRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Override
    public void save(Issue entity) {
        issueRepository.save(entity);
    }

    @Override
    @CacheEvict(cacheNames = "issue", key = "#id")
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = issueRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            issueRepository.deleteById(id);
        }
    }

    @Override
    @CachePut(value = "issue", key = "#entity.id")
    public Issue update(Issue entity) {
        boolean wasUpdated = issueRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            issueRepository.save(entity);
        }
        return entity;
    }

}
