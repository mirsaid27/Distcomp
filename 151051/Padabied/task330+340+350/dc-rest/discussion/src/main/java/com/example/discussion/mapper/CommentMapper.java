package com.example.discussion.mapper;

import com.example.discussion.model.dto.CommentRequestTo;
import com.example.discussion.model.dto.CommentResponseTo;
import com.example.discussion.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "storyId", target = "storyId")
    Comment toEntity(CommentRequestTo commentRequestTo);

    @Mapping(source = "comment.storyId", target = "storyId")
    CommentResponseTo toResponse(Comment comment);
}
