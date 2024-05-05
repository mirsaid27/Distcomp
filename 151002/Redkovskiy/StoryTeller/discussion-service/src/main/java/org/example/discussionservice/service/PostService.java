package org.example.discussionservice.service;

import org.example.discussionservice.dto.PostDto;
import org.example.discussionservice.model.Post;

import java.util.List;

public interface PostService {

    PostDto getPostDtoById(Long id);

    List<PostDto> getAllPostDtos();

    PostDto addPost(PostDto post);

    PostDto updatePost(PostDto post);

    PostDto deletePostById(Long id);

}
