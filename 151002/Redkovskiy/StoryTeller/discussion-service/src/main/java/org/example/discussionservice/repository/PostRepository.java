package org.example.discussionservice.repository;

import org.example.discussionservice.model.Post;
import org.example.discussionservice.model.PostId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CassandraRepository<Post, PostId> {

    @Query("SELECT * FROM tbl_post WHERE id = ?0 ALLOW FILTERING;")
    Optional<Post> findById(Long id);

}
