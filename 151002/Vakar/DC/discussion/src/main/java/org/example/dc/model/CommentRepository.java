package org.example.dc.model;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CommentRepository extends CassandraRepository<Comment, Integer> {
}