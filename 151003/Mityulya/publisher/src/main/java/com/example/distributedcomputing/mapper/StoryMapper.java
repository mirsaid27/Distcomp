package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.User;
import com.example.distributedcomputing.model.entity.Tweet;
import com.example.distributedcomputing.model.request.TweetRequestTo;
import com.example.distributedcomputing.model.response.TweetResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Tweet dtoToEntity(TweetRequestTo tweetRequestTo);
    List<User> dtoToEntity(Iterable<Tweet> stories);

    TweetResponseTo entityToDto(Tweet tweet);

    List<TweetResponseTo> entityToDto(Iterable<Tweet> stories);
}
