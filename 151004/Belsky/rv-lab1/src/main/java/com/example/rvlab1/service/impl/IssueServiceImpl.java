package com.example.rvlab1.service.impl;

import com.example.rvlab1.exception.ServiceErrorCode;
import com.example.rvlab1.exception.ServiceException;
import com.example.rvlab1.model.service.Issue;
import com.example.rvlab1.service.IssueService;
import com.example.rvlab1.storage.IssueStorage;
import com.example.rvlab1.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueStorage issueStorage;
    private final UserStorage userStorage;

    @Override
    public List<Issue> getAll() {
        return issueStorage.findAll();
    }

    @Override
    public Issue saveIssue(Issue issue) {
        validateIssueToSave(issue);
        if (Objects.nonNull(issue.getUserId()) && !userStorage.existsById(issue.getUserId())) {
            throw new ServiceException("User with such id does not exist", ServiceErrorCode.ENTITY_NOT_FOUND);
        }
        return issueStorage.save(issue);
    }

    @Override
    public Issue findById(Long issueId) {
        return issueStorage.findById(issueId)
                .orElseThrow(() -> new ServiceException("Issue not found", ServiceErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public void deleteIssue(Issue issue) {
        issueStorage.delete(issue);
    }

    private void validateIssueToSave(Issue issue) {
        if (Objects.isNull(issue.getTitle()) || issue.getTitle().length() < 2 || issue.getTitle().length() > 64
                || (Objects.nonNull(issue.getContent()) && (issue.getContent().length() < 4 || issue.getContent().length() > 2048))
        ) {
            throw new ServiceException("Issue не валидна", ServiceErrorCode.BAD_REQUEST);
        }
        if (Objects.isNull(issue.getId()) && issueStorage.existsByTitle(issue.getTitle())) {
            throw new ServiceException("Duplicate title issue", ServiceErrorCode.DUPLICATE_TITLE_ISSUE);
        }
    }
}
