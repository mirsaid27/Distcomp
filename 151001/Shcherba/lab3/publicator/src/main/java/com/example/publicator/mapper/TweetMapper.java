package com.example.publicator.mapper;

import com.example.publicator.dto.TweetRequestTo;
import com.example.publicator.dto.TweetResponseTo;
import com.example.publicator.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    Tweet tweetRequestToTweet(TweetRequestTo tweetRequestTo);
    @Mapping(target = "userId", source = "tweet.user.id")

    TweetResponseTo tweetToTweetResponse(Tweet tweet);
}
