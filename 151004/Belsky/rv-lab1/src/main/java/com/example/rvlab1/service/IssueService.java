package com.example.rvlab1.service;

import com.example.rvlab1.model.service.Issue;

import java.util.List;

public interface IssueService {
    List<Issue> getAll();

    Issue saveIssue(Issue issue);

    Issue findById(Long issueId);

    void deleteIssue(Issue issue);
}
