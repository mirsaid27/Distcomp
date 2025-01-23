package com.example.repository;

import com.example.entities.Post;
import com.example.entities.PostKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface PostRepository extends CassandraRepository<Post, PostKey> {
    List<Post> findByIssueId(Long issueId);
    List<Post> findById (Long id);
    void deleteByCountryAndIssueIdAndId (String country, Long issueId, Long id);
    int countByCountry (String country);
}
