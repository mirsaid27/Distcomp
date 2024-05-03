package com.example.rvlab1.service;

import com.example.rvlab1.model.service.Post;

import java.util.List;

public interface PostService {
    List<Post> getAll();

    Post createPost(Post post);

    Post updatePost(Post post);

    Post findById(Long postId);

    void deletePost(Post post);
}
