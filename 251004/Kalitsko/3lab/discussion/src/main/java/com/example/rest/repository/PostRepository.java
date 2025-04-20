package com.example.rest.repository;

import com.example.rest.entity.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, String> {

    Optional<Post> findById(Long id);
    void deleteById(Long id);
}
