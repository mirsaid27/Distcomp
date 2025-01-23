package com.yankovich.dc_rest.mapper;

import com.yankovich.dc_rest.model.Tweet;
import com.yankovich.dc_rest.model.dto.requests.TweetRequestTo;
import com.yankovich.dc_rest.model.dto.responses.TweetResponseTo;
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