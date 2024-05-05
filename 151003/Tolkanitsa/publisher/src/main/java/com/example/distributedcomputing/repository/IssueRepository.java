package com.example.distributedcomputing.repository;

import com.example.distributedcomputing.model.entity.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {
    Optional<Issue> findByTitle(String title);
}
