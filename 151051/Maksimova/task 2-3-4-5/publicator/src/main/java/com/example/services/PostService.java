package com.example.services;

import com.example.dto.PostRequestTo;
import com.example.dto.PostResponseTo;
import com.example.entities.Post;
import com.example.mapper.PostMapper;
import com.example.repository.PostRepository;
import com.example.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Transactional
    public PostResponseTo createPost(PostRequestTo postRequestTo) {
        Post post = postMapper.postRequestToPost(postRequestTo);
        post = postRepository.save(post);
        return postMapper.postToPostResponse(post);
    }

    @Transactional(readOnly = true)
    public PostResponseTo getPostById(Long id) throws NotFoundException {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isPresent()) {
            return postMapper.postToPostResponse(postOpt.get());
        } else {
            throw new NotFoundException("Post not found!", 404L);
        }
    }

    @Transactional
    public PostResponseTo updatePost(Long id, PostRequestTo postRequestTo) throws NotFoundException {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            post.setContent(postRequestTo.getContent());
            post.setIssueId(postRequestTo.getIssueId());
            post.setCountry(postRequestTo.getCountry());
            post = postRepository.save(post);
            return postMapper.postToPostResponse(post);
        } else {
            throw new NotFoundException("Post not found!", 404L);
        }
    }

    @Transactional
    public void deletePost(Long id) throws NotFoundException {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isPresent()) {
            postRepository.delete(postOpt.get());
        } else {
            throw new NotFoundException("Post not found!", 404L);
        }
    }

    @Transactional(readOnly = true)
    public List<PostResponseTo> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return postMapper.postListToPostResponseList(posts);
    }
}



