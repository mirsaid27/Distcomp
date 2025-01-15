package com.example.service;

import com.example.repository.PostRepository;
import com.example.request.PostRequestTo;
import com.example.response.PostResponseTo;
import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.ResourceStateException;
import com.example.mapper.PostMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class PostService implements IService<PostRequestTo, PostResponseTo>{
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public PostResponseTo findById(Long id) {
        return postRepository.getById(id).map(postMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<PostResponseTo> findAll() {
        return postMapper.getListResponse(postRepository.getAll());
    }

    @Override
    public PostResponseTo create(PostRequestTo request) {
        return postRepository.save(postMapper.getPost(request)).map(postMapper::getResponse).orElseThrow(PostService::createException);
    }

    @Override
    public PostResponseTo update(PostRequestTo request) {
        if (postMapper.getPost(request).getId() == null)
        {
            throw findByIdException(postMapper.getPost(request).getId());
        }

        return postRepository.update(postMapper.getPost(request)).map(postMapper::getResponse).orElseThrow(PostService::updateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!postRepository.removeById(id)) {
            throw removeException();
        }
        return true;
    }

    private static ResourceNotFoundException findByIdException(Long id) {
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