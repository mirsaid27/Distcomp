package by.harlap.jpa.mapper;

import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.model.Tweet;
import org.mapstruct.*;

@Mapper
public interface TweetMapper {

    @Mapping(target="editorId", source="editor.id")
    TweetResponseDto toTweetResponse(Tweet tweet);

    @Mapping(target = "editor.id", source = "editorId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tweet toTweet(UpdateTweetDto tweetRequest, @MappingTarget Tweet tweet);

    @Mapping(target = "editor.id", source = "editorId")
    Tweet toTweet(CreateTweetDto tweetRequest);
}
