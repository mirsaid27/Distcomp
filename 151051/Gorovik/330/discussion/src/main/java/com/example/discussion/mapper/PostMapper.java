package com.example.mapper;

import com.example.model.Post;
import com.example.request.PostRequestTo;
import com.example.response.PostResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface PostMapper {

    PostRequestTo postToRequestTo(Post post);

    List<PostRequestTo> postToRequestTo(Iterable<Post> posts);

    Post dtoToEntity(PostRequestTo postRequestTo, String country);

    PostResponseTo postToResponseTo(Post post);

    List<PostResponseTo> postToResponseTo(Iterable<Post> post);
}