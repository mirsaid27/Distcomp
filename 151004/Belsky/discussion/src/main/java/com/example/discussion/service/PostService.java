package com.example.discussion.service;

import com.example.discussion.model.service.Post;

import java.util.List;

public interface PostService {
    List<Post> getAll();

    Post savePost(Post post);

    Post findById(Long postId);

    void deletePost(Post post);
}
