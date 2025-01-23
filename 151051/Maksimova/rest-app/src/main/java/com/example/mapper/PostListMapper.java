package com.example.mapper;

import com.example.api.dto.PostRequestTo;
import com.example.api.dto.PostResponseTo;
import com.example.entities.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface PostListMapper {
    List<Post> toPostList(List<PostRequestTo> Posts);
    List<PostResponseTo> toPostResponseList(List<Post> Posts);
}
