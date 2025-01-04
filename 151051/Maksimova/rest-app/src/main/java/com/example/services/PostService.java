package com.example.services;

import com.example.dao.PostDao;
import com.example.api.dto.PostRequestTo;
import com.example.api.dto.PostResponseTo;
import com.example.entities.Post;
import com.example.exceptions.DeleteException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.UpdateException;
import com.example.mapper.PostListMapper;
import com.example.mapper.PostMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PostService {
    @Autowired
    PostMapper PostMapper;
    @Autowired
    PostDao PostDao;
    @Autowired
    PostListMapper PostListMapper;

    public PostResponseTo getPostById(@Min(0) Long id) throws NotFoundException {
        Optional<Post> Post = PostDao.findById(id);
        return Post.map(value -> PostMapper.PostToPostResponse(value)).orElseThrow(() -> new NotFoundException("Post not found!", 40004L));
    }

    public List<PostResponseTo> getPosts() {
        return PostListMapper.toPostResponseList(PostDao.findAll());
    }

    public PostResponseTo savePost(@Valid PostRequestTo Post) {
        Post PostToSave = PostMapper.PostRequestToPost(Post);
        return PostMapper.PostToPostResponse(PostDao.save(PostToSave));
    }

    public void deletePost(@Min(0) Long id) throws DeleteException {
        PostDao.delete(id);
    }

    public PostResponseTo updatePost(@Valid PostRequestTo Post) throws UpdateException {
        Post PostToUpdate = PostMapper.PostRequestToPost(Post);
        return PostMapper.PostToPostResponse(PostDao.update(PostToUpdate));
    }

    public PostResponseTo getPostByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Post> Post = PostDao.getPostByIssueId(issueId);
        return Post.map(value -> PostMapper.PostToPostResponse(value)).orElseThrow(() -> new NotFoundException("Post not found!", 40004L));
    }
}