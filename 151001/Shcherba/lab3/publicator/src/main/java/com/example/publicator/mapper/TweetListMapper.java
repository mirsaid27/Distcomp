package com.example.publicator.mapper;

import com.example.publicator.dto.TweetRequestTo;
import com.example.publicator.dto.TweetResponseTo;
import com.example.publicator.model.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TweetMapper.class)
public interface TweetListMapper {
    List<Tweet> toTweetList(List<TweetRequestTo> list);

    List<TweetResponseTo> toTweetResponseList(List<Tweet> list);
}
