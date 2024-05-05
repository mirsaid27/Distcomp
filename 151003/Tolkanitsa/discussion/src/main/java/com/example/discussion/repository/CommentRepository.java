package com.example.discussion.repository;

import com.example.discussion.model.entity.Comment;
import com.example.discussion.model.entity.CommentKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, CommentKey> {
    Optional<Comment> findById (Long id);
    int countByCountry(String country);

    void deleteByCountryAndIssueIdAndId (String country, Long issueId, Long id);
}
