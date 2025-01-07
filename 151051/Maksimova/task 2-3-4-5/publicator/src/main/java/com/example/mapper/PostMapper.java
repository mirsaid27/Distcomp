package com.example.mapper;

import com.example.dto.PostRequestTo;
import com.example.dto.PostResponseTo;
import com.example.entities.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post postRequestToPost(PostRequestTo postRequestTo);
    PostResponseTo postToPostResponse(Post post);

    List<PostResponseTo> postListToPostResponseList(List<Post> posts);
}


