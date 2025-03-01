package by.ryabchikov.tweet_service.repository.impl;

import by.ryabchikov.tweet_service.entity.Comment;
import by.ryabchikov.tweet_service.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final Map<Long, Comment> comments = new HashMap<>();

    @Override
    public Comment save(Comment comment) {
        comment.setId(Math.abs(new Random().nextLong()));
        comments.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public List<Comment> findAll() {
        return comments.values().stream().toList();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(comments.get(id));
    }

    @Override
    public void deleteById(Long id) {
        comments.remove(id);
    }
}