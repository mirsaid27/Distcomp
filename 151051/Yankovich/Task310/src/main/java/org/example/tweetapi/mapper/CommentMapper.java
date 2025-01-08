package org.example.tweetapi.mapper;

import org.example.tweetapi.model.dto.request.CommentRequestTo;
import org.example.tweetapi.model.dto.response.CommentResponseTo;
import org.example.tweetapi.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "tweet.id", target = "tweetId")
    CommentResponseTo toResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    Comment toEntity(CommentRequestTo commentRequestDto);
}