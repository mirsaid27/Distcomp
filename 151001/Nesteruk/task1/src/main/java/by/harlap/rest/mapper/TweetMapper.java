package by.harlap.rest.mapper;

import by.harlap.rest.dto.request.CreateTweetDto;
import by.harlap.rest.dto.request.UpdateTweetDto;
import by.harlap.rest.dto.response.TweetResponseDto;
import by.harlap.rest.model.Tweet;
import org.mapstruct.*;

@Mapper
public interface TweetMapper {

    TweetResponseDto toTweetResponse(Tweet tweet);

    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    Tweet toTweet(CreateTweetDto tweetRequest);

    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tweet toTweet(UpdateTweetDto tweetRequest, @MappingTarget Tweet tweet);
}
