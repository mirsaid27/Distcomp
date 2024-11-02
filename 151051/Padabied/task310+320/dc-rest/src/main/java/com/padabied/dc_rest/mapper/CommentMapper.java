package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.Comment;
import com.padabied.dc_rest.model.dto.requests.CommentRequestTo;
import com.padabied.dc_rest.model.dto.responses.CommentResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "story.id", target = "storyId")
    CommentResponseTo toResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    Comment toEntity(CommentRequestTo commentRequestDto);
}
