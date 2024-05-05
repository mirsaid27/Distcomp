package com.example.lab2.Mapper;

import com.example.lab2.DTO.PostRequestTo;
import com.example.lab2.DTO.PostResponseTo;
import com.example.lab2.Model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post postRequestToPost(PostRequestTo postRequestTo);
    @Mapping(target = "issueId", source = "post.issue.id")
    PostResponseTo postToPostResponse(Post post);
}
