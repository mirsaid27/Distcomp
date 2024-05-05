package com.example.discussion.mapper;

import com.example.discussion.model.entity.Comment;
import com.example.discussion.model.request.CommentRequestTo;
import com.example.discussion.model.response.CommentResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapperDisc {
    CommentResponseTo entityToResponse(Comment entity);
    Comment requestToEntity(CommentRequestTo request);
}
