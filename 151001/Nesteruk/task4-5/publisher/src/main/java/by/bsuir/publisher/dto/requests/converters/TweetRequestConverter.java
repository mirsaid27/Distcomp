package by.bsuir.publisher.dto.requests.converters;

import by.bsuir.publisher.domain.Tweet;
import by.bsuir.publisher.dto.requests.TweetRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetRequestConverter {
    @Mapping(source = "editorId", target = "editor.id")
    Tweet fromDto(TweetRequestDto tweet);
}
