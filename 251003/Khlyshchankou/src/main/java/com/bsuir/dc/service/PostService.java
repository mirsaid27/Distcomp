package com.bsuir.dc.service;

import com.bsuir.dc.dao.InMemoryPostDao;
import com.bsuir.dc.dto.request.PostRequestTo;
import com.bsuir.dc.dto.response.PostResponseTo;
import com.bsuir.dc.exception.EntityNotFoundException;
import com.bsuir.dc.model.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final ModelMapper modelMapper;
    private final InMemoryPostDao postDao;

    @Autowired
    public PostService(ModelMapper modelMapper, InMemoryPostDao postDao) {
        this.modelMapper = modelMapper;
        this.postDao = postDao;
    }

    private Post convertToPost(PostRequestTo postRequestTo) {
        return this.modelMapper.map(postRequestTo, Post.class);
    }

    private PostResponseTo convertToResponse(Post post) {
        return this.modelMapper.map(post, PostResponseTo.class);
    }

    public PostResponseTo create(PostRequestTo postRequestTo) {
        Post post = convertToPost(postRequestTo);
        postDao.save(post);

        return convertToResponse(post);
    }

    public List<PostResponseTo> findAll() {
        return postDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PostResponseTo findById(long id) throws EntityNotFoundException {
        Post post = postDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This post doesn't exist."));

        return convertToResponse(post);
    }

    public List<PostResponseTo> findByTopicId(long topicId) {
        return postDao.findByTopicId(topicId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PostResponseTo update(PostRequestTo postRequestTo) throws EntityNotFoundException {
        postDao.findById(postRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This post doesn't exist."));

        Post updatedPost = convertToPost(postRequestTo);
        updatedPost.setId(postRequestTo.getId());
        postDao.save(updatedPost);

        return convertToResponse(updatedPost);
    }

    public PostResponseTo partialUpdate(PostRequestTo postRequestTo) throws EntityNotFoundException {
        Post post = postDao.findById(postRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This post doesn't exist."));

        if (postRequestTo.getContent() != null) {
            post.setContent(postRequestTo.getContent());
        }
        if (postRequestTo.getTopicId() != 0) {
            post.setTopicId(postRequestTo.getTopicId());
        }
        postDao.save(post);

        return convertToResponse(post);
    }

    public void delete(long id) throws EntityNotFoundException {
        postDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This post doesn't exist."));
        postDao.deleteById(id);
    }
}
