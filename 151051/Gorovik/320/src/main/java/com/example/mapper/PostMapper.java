package com.example.mapper;

import com.example.model.Post;
import com.example.request.PostRequestTo;
import com.example.response.PostResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponseTo getResponse(Post post);
    List<PostResponseTo> getListResponse(Iterable<Post> posts);
    Post getPost(PostRequestTo postRequestTo);
}