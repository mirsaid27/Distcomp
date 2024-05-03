package com.example.discussion.repository;

import com.example.discussion.model.entity.PostEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PostRepository extends CassandraRepository<PostEntity, Long> {
}
