package com.example.rvlab1.storage.impl;

import com.example.rvlab1.mapper.IssueMapper;
import com.example.rvlab1.model.service.Issue;
import com.example.rvlab1.repository.IssueRepository;
import com.example.rvlab1.storage.IssueStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IssueStorageImpl implements IssueStorage {
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    @Override
    public List<Issue> findAll() {
        return issueRepository.findAll().stream()
                .map(issueMapper::mapToBO).toList();
    }

    @Override
    public Issue save(Issue issue) {
        return issueMapper.mapToBO(issueRepository.save(issueMapper.mapToEntity(issue)));
    }

    @Override
    public Optional<Issue> findById(Long id) {
        return issueRepository.findById(id).map(issueMapper::mapToBO);
    }

    @Override
    public void delete(Issue issue) {
        issueRepository.deleteById(issue.getId());
    }

    @Override
    public boolean existsByTitle(String title) {
        return issueRepository.existsByTitle(title);
    }

    @Override
    public boolean existsById(Long id) {
        return issueRepository.existsById(id);
    }
}
