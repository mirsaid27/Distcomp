package by.kopvzakone.distcomp.dto;

import by.kopvzakone.distcomp.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TweetMapper {
    TweetResponseTo out(Tweet tweet);
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    Tweet in(TweetRequestTo tweet);
}
