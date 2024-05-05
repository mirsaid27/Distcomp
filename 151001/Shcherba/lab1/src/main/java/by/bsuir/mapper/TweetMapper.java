package by.bsuir.mapper;

import by.bsuir.dto.TweetRequestTo;
import by.bsuir.dto.TweetResponseTo;
import by.bsuir.entities.Tweet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    Tweet tweetRequestToTweet (TweetRequestTo tweetRequestTo);
    TweetResponseTo tweetToTweetResponse(Tweet tweet);
}
