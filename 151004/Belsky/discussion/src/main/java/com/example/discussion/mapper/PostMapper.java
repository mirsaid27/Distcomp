package com.example.discussion.mapper;

import com.example.discussion.model.dto.request.PostRequestTo;
import com.example.discussion.model.dto.request.PostRequestWithIdTo;
import com.example.discussion.model.dto.response.PostResponseTo;
import com.example.discussion.model.entity.PostEntity;
import com.example.discussion.model.service.Post;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post mapToBO(PostEntity postEntity);

    PostEntity mapToEntity(Post post);

    Post mapToBO(PostRequestTo postRequestTo);

    PostResponseTo mapToResponseTo(Post post);

    @InheritConfiguration
    void updatePostRequestToToPostBo(PostRequestWithIdTo postRequestWithIdTo, @MappingTarget Post post);
}
