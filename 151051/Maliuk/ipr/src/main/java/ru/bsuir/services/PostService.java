package ru.bsuir.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.PostRequestTo;
import ru.bsuir.dto.response.PostResponseTo;
import ru.bsuir.entity.Post;
import ru.bsuir.entity.Story;
import ru.bsuir.irepositories.IPostRepository;
import ru.bsuir.irepositories.IStoryRepository;
import ru.bsuir.mapper.PostMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class PostService {

    private final IPostRepository postRepository;

    private final PostMapper postMapper;

    private final IStoryRepository postRepository;

    @CacheEvict(value = {"posts", "postsList"}, allEntries = true)
    public PostResponseTo createPost(PostRequestTo postRequest) {

        Story story = postRepository.findById(postRequest.getTweetId())
                .orElseThrow(() -> new DataIntegrityViolationException("Story not found"));

        Post post = postMapper.toEntity(postRequest);
        post.setStory(story);
        post = postRepository.save(post);
        return postMapper.toDTO(post);
    }

    @Cacheable(value = "posts", key = "#id")
    public PostResponseTo getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(postMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Cacheable(value = "postsList")
    public List<PostResponseTo> getAllPost(){
        return postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = {"posts", "cpostsList"}, key = "#id", allEntries = true)
    public PostResponseTo updatePost(Long id, PostRequestTo postRequest) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(postRequest.getContent());
        postRepository.save(post);
        return postMapper.toDTO((post));
    }

    @CacheEvict(value = {"posts", "postsList"}, key = "#id", allEntries = true)
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");

        }
        postRepository.deleteById(id);
    }


}
