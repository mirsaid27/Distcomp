package com.bsuir.dc.dao;

import com.bsuir.dc.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryPostDao {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idGenerator.getAndIncrement());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public List<Post> findByTopicId(long topicId) {
        return posts.values().stream()
                .filter(post -> post.getTopicId() == topicId)
                .collect(Collectors.toList());
    }

    public void deleteById(long id) {
        posts.remove(id);
    }
}
