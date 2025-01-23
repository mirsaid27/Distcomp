package com.example.service;

import com.example.discussion.model.Post;
import com.example.discussion.mapper.PostMapperImpl;
import com.example.discussion.repository.PostRepository;
import com.example.discussion.request.PostRequestTo;
import com.example.discussion.response.PostResponseTo;
import com.example.discussion.exceptions.ResourceNotFoundException;
import com.example.discussion.exceptions.ResourceStateException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service

@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;
    private final PostMapperImpl postMapper;
    private final String ISSUE_PATH = "http://localhost:24110/api/v1.0/news/";

    public PostResponseTo findById(BigInteger id) {
        return postRepository.getById("local", id).map(postMapper::postToResponseTo).orElseThrow(() -> findByIdException(id));
    }


    public List<PostResponseTo> findAll() {
        List<PostResponseTo> res = new ArrayList<>();
        for(Post curr:postRepository.findAll()){
            res.add(postMapper.postToResponseTo(curr));
        }
        return res;
    }


    public PostResponseTo create(PostRequestTo request) {
        Post post = postMapper.dtoToEntity(request, "local");
        if (post.getId() == null) {
            post.setId(BigInteger.valueOf(UUID.randomUUID().getMostSignificantBits()).abs());
        }
        try {
            postRepository.savePost(post);
        } catch (DataIntegrityViolationException e) {
            createException();
        }
        return postMapper.postToResponseTo(post);
    }


    public PostResponseTo update(PostRequestTo request) throws ResourceStateException{
        if (postRepository.getById("local", new BigInteger(request.id().toString())).isEmpty()) {
            updateException();
        }
        Post entity = postMapper.dtoToEntity(request, "local");
        try {
            postRepository.updatePost(entity);
        } catch (DataIntegrityViolationException e) {
            updateException();
        }
        return postMapper.postToResponseTo(entity);
    }


    public boolean removeById(BigInteger id) {

        postRepository.removeById("local", id);
        return true;
    }

    private static ResourceNotFoundException findByIdException(BigInteger id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 31, "Can't find post by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 32, "Can't create post");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 33, "Can't update post");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 34, "Can't remove post");
    }
}