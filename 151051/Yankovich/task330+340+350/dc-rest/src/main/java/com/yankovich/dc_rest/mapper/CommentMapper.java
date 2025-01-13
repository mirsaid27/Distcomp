package com.yankovich.dc_rest.mapper;

import com.yankovich.dc_rest.model.Comment;
import com.yankovich.dc_rest.model.dto.requests.CommentRequestTo;
import com.yankovich.dc_rest.model.dto.responses.CommentResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "tweet.id", target = "tweetId")
    CommentResponseTo toResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    Comment toEntity(CommentRequestTo commentRequestDto);
}
