package com.example.discussion.repository;

import com.example.discussion.model.Comment;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CommentRepository extends CassandraRepository<Comment, Long> {
}
