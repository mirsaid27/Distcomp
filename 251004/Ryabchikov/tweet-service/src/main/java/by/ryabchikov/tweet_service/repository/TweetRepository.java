package by.ryabchikov.tweet_service.repository;

import by.ryabchikov.tweet_service.entity.Tweet;

import java.util.List;
import java.util.Optional;

public interface TweetRepository {
    Tweet save(Tweet tweet);

    List<Tweet> findAll();

    Optional<Tweet> findById(Long id);

    void deleteById(Long id);
}
