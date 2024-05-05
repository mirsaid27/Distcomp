package com.example.rvlab1.restclient;

import com.example.rvlab1.model.dto.request.PostRequestTo;
import com.example.rvlab1.model.dto.request.PostRequestWithIdTo;
import com.example.rvlab1.model.dto.response.PostResponseTo;

import java.util.List;

public interface PostApiClient {
    List<PostResponseTo> getAllPosts();

    PostResponseTo createPost(PostRequestTo postRequestTo);

    void deletePostById(Long postId);

    PostResponseTo getPostById(Long postId);

    PostResponseTo updatePost(PostRequestWithIdTo postRequestTo);
}
