package com.yankovich.dc_rest.repository.interfaces;

import com.yankovich.dc_rest.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
