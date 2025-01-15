package ru.bsuir.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.PostRequestTo;
import ru.bsuir.dto.response.PostResponseTo;
import ru.bsuir.entity.Post;
import ru.bsuir.irepositories.IPostRepository;
import ru.bsuir.mapper.PostMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service

public class PostService {

    private final IPostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostService(IPostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public PostResponseTo createPost(PostRequestTo postRequest) {
        Post post = postMapper.toEntity(postRequest);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    public Optional<PostResponseTo> getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(postMapper::toDTO);
    }

    public List<PostResponseTo> getAllPost(){
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .map(postMapper::toDTO)
                .toList();
    }

    public Optional<PostResponseTo> updatePost(Long id, PostRequestTo postRequest) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            Post postComment = postMapper.toEntity(postRequest);
            postComment.setId(id);
            postRepository.save(postComment);
            return Optional.of(postMapper.toDTO(postComment));
        }
        throw new EntityNotFoundException("Post not found with id: " + id);
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
