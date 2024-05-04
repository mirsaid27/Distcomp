package org.example.discussionservice.service;

import org.example.discussionservice.dto.PostDto;
import org.example.discussionservice.model.Post;
import org.example.discussionservice.model.PostId;
import org.example.discussionservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto getPostDtoById(Long id) {
        return postToPostDto(getPostById(id));
    }

    @Override
    public List<PostDto> getAllPostDtos() {
        return getAllPosts().stream().map(this::postToPostDto).toList();
    }

    @Override
    public PostDto addPost(PostDto postDto) {
        Post post = postDtoToPost(postDto);
        if (isDuplicateRecord(post.getPostId().getId())) {
            return null;
        }
        return postToPostDto(postRepository.save(post));
    }

    @Override
    public PostDto updatePost(PostDto post) {
        Post retrievedPost = getPostById(post.getId());
        retrievedPost.setContent(post.getContent());
        return postToPostDto(postRepository.save(retrievedPost));
    }

    @Override
    public PostDto deletePostById(Long id) {
        Post post = getPostById(id);
        if (post != null) {
            postRepository.delete(post);
            return postToPostDto(post);
        }
        return null;
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    private List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    private boolean isDuplicateRecord(Long postId) {
        return postRepository.findById(postId).isPresent();
    }

    private Post postDtoToPost(PostDto postDto) {
        PostId postId = new PostId(
                generateRandomFourCharString(),
                generateRandomId(),
                postDto.getStoryId()
        );
        return new Post(postId, postDto.getContent());
    }

    private PostDto postToPostDto(Post post) {

        return (post != null) ? new PostDto(
                post.getPostId().getId(),
                post.getContent(),
                post.getPostId().getStoryId()
        ) : null;
    }

    private String generateRandomFourCharString() {
        final int leftLimit = 97; // ASCII value for 'a'
        final int rightLimit = 122; // ASCII value for 'z'
        final int targetStringLength = 4;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static Long generateRandomId() {
        Random random = new Random();
        long maxRange = (long) Math.pow(10, 10) - 1; // Maximum 10-digit value
        return Math.abs(random.nextLong() % maxRange);
    }
}
