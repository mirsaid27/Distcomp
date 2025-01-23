package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.TweetRequestTo;
import ru.bsuir.dto.response.TweetResponseTo;
import ru.bsuir.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Tweet toEntity(TweetRequestTo dto);

    @Mapping(source = "editor.id", target = "editorId")
    TweetResponseTo toDTO(Tweet entity);
}
