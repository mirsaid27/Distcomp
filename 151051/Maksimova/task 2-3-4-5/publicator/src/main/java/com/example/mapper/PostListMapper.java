package com.example.mapper;

import com.example.dto.PostRequestTo;
import com.example.dto.PostResponseTo;
import com.example.entities.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostListMapper {
    List<Post> toPostList(List<PostRequestTo> posts);
    List<PostResponseTo> toPostResponseList(List<Post> posts);
}
