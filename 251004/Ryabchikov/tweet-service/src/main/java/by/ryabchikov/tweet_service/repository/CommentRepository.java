package by.ryabchikov.tweet_service.repository;

import by.ryabchikov.tweet_service.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    void deleteById(Long id);
}
