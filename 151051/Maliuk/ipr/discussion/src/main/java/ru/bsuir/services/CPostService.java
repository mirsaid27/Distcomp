package ru.bsuir.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.CPostRequestTo;
import ru.bsuir.dto.response.CPostResponseTo;
import ru.bsuir.entity.CPost;
import ru.bsuir.irepositories.CPostRepository;
import ru.bsuir.mapper.CPostMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CPostService {

    private final CPostRepository postRepository;
    private final CPostMapper postMapper;

    public CPostResponseTo createPost(CPostRequestTo postRequest) {
        CPost post = postMapper.toEntity(postRequest);

        try {
            post = postRepository.save(post);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Post already exists or invalid data");
        }
        return postMapper.toDTO(post);
    }

    public CPostResponseTo getPostById(Long id) {
        Optional<CPost> postOpt = postRepository.findById(id);
        return postOpt
                .map(postMapper::toDTO)
                .orElseGet(() -> createNotFoundResponse(id));
    }

    private CPostResponseTo createNotFoundResponse(Long id) {
        CPostResponseTo response = new CPostResponseTo();
        response.setErrorExist(true);
        return response;
    }

    public List<CPostResponseTo> getAllPost(){
        return postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public CPostResponseTo updatePost(Long id, CPostRequestTo postRequest) {
        CPost existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        existingPost.setContent(postRequest.getContent());

        postRepository.save(existingPost);
        return postMapper.toDTO(existingPost);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");

        }
        postRepository.deleteById(id);
    }


}

