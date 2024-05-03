package com.example.rvlab1.storage;

import com.example.rvlab1.model.service.Issue;

public interface IssueStorage extends CRUDStorage<Issue, Long> {
    boolean existsByTitle(String title);

    boolean existsById(Long id);
}
