package com.example.rvlab1.service.impl;

import com.example.rvlab1.exception.ServiceErrorCode;
import com.example.rvlab1.exception.ServiceException;
import com.example.rvlab1.mapper.PostMapper;
import com.example.rvlab1.model.service.Post;
import com.example.rvlab1.restclient.PostApiClient;
import com.example.rvlab1.service.IssueService;
import com.example.rvlab1.service.PostService;
import com.example.rvlab1.storage.IssueStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostApiClient postApiClient;
    private final PostMapper postMapper;
    private final IssueStorage issueStorage;

    @Override
    public List<Post> getAll() {
        return postApiClient.getAllPosts().stream()
                .map(postMapper::mapToBo).toList();
    }

    @Override
    public Post createPost(Post post) {
        if (!issueStorage.existsById(post.getIssueId())) {
            throw new ServiceException("", ServiceErrorCode.BAD_REQUEST);
        }
        return postMapper.mapToBo(
                postApiClient.createPost(postMapper.mapToPostRequestTo(post))
        );
    }

    @Override
    public Post updatePost(Post post) {
        return postMapper.mapToBo(
                postApiClient.updatePost(postMapper.mapToPostRequestWithIdTo(post))
        );
    }

    @Override
    public Post findById(Long postId) {
        return postMapper.mapToBo(postApiClient.getPostById(postId));
    }

    @Override
    public void deletePost(Post post) {
        postApiClient.deletePostById(post.getId());
    }
}
