package com.example.discussion.storage.impl;

import com.example.discussion.mapper.PostMapper;
import com.example.discussion.model.service.Post;
import com.example.discussion.repository.PostRepository;
import com.example.discussion.storage.PostStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostStorageImpl implements PostStorage {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToBO).toList();
    }

    @Override
    public Post save(Post post) {
        return postMapper.mapToBO(postRepository.save(postMapper.mapToEntity(post)));
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id)
                .map(postMapper::mapToBO);
    }

    @Override
    public void delete(Post post) {
        postRepository.deleteById(post.getId());
    }
}
