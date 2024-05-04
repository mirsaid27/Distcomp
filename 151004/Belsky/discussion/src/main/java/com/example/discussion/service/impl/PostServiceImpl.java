package com.example.discussion.service.impl;

import com.example.discussion.exception.ServiceErrorCode;
import com.example.discussion.exception.ServiceException;
import com.example.discussion.model.service.Post;
import com.example.discussion.service.PostService;
import com.example.discussion.storage.PostStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostStorage postStorage;

    @Override
    public List<Post> getAll() {
        return postStorage.findAll();
    }

    @Override
    public Post savePost(Post post) {
        validatePostToSave(post);
        return postStorage.save(post);
    }

    @Override
    public Post findById(Long postId) {
        return postStorage.findById(postId)
                .orElseThrow(() -> new ServiceException("Post not found", ServiceErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public void deletePost(Post post) {
        postStorage.delete(post);
    }

    private void validatePostToSave(Post post) {
        if (post.getContent().length() < 2 || post.getContent().length() > 2048) {
            throw new ServiceException("Post не валиден", ServiceErrorCode.BAD_REQUEST);
        }
    }
}
