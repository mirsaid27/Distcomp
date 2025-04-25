package com.bsuir.discussion.services;

import com.bsuir.discussion.dto.requests.PostRequestDTO;
import com.bsuir.discussion.dto.responses.PostResponseDTO;
import com.bsuir.discussion.models.Post;
import com.bsuir.discussion.repositories.PostsRepository;
import com.bsuir.discussion.utils.mappers.PostsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final PostsMapper postsMapper;
    @Value("${post.country}")
    private String country;

    @Autowired
    public PostsService(PostsRepository postsRepository, PostsMapper postsMapper) {
        this.postsRepository = postsRepository;
        this.postsMapper = postsMapper;
    }

    public PostResponseDTO save(PostRequestDTO postRequestDTO) {
        Post post = postsMapper.toPost(postRequestDTO);
        post.getKey().setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        return postsMapper.toPostResponse(postsRepository.save(post));
    }

    public List<PostResponseDTO> findAll() {
        return postsMapper.toPostResponseList(postsRepository.findAll());
    }

    public PostResponseDTO findById(Long id) {
        return postsMapper.toPostResponse(
                postsRepository.findByCountryAndId(country, id)
                        .orElseThrow(() -> new RuntimeException(String.valueOf(id)))
        );
    }

    public void deleteById(long id) {
        postsRepository.deleteByCountryAndId(country, id);
    }

    public PostResponseDTO update(PostRequestDTO postRequestDTO) {
        Post post = postsMapper.toPost(postRequestDTO);
        post.getKey().setId(postRequestDTO.getId());
        return postsMapper.toPostResponse(postsRepository.save(post));
    }
}
