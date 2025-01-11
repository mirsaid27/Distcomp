package org.example.tweetapi.mapper;

import org.example.tweetapi.model.dto.request.TweetRequestTo;
import org.example.tweetapi.model.dto.response.TweetResponseTo;
import org.example.tweetapi.model.entity.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    @Mapping(source = "author.id", target = "authorId")
    TweetResponseTo toResponse(Tweet tweet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Tweet toEntity(TweetRequestTo tweetRequestDto);
}