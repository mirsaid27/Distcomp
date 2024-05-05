package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Comment;
import com.example.distributedcomputing.model.request.CommentRequestTo;
import com.example.distributedcomputing.model.response.CommentResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment dtoToEntity(CommentRequestTo commentRequestTo);
    List<Comment> dtoToEntity(Iterable<CommentRequestTo> notes);

    CommentResponseTo entityToDto(Comment note);

    List<CommentResponseTo> entityToDto(Iterable<Comment> notes);
}
