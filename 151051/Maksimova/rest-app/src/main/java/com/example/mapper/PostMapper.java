package com.example.mapper;

import com.example.api.dto.PostRequestTo;
import com.example.api.dto.PostResponseTo;
import com.example.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post PostRequestToPost(PostRequestTo PostRequestTo);

    PostResponseTo PostToPostResponse(Post Post);
}
