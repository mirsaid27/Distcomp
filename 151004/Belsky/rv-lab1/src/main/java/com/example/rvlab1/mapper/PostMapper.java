package com.example.rvlab1.mapper;

import com.example.rvlab1.model.dto.request.PostRequestTo;
import com.example.rvlab1.model.dto.request.PostRequestWithIdTo;
import com.example.rvlab1.model.dto.response.PostResponseTo;
import com.example.rvlab1.model.service.Post;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post mapToBo(PostResponseTo postResponseTo);

    PostRequestTo mapToPostRequestTo(Post post);

    PostRequestWithIdTo mapToPostRequestWithIdTo(Post post);

    Post mapToBO(PostRequestTo postRequestTo);

    PostResponseTo mapToResponseTo(Post post);

    @InheritConfiguration
    void updatePostRequestToToPostBo(PostRequestWithIdTo postRequestWithIdTo, @MappingTarget Post post);
}
