package by.ryabchikov.tweet_service.repository.impl;

import by.ryabchikov.tweet_service.entity.Tweet;
import by.ryabchikov.tweet_service.repository.TweetRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Repository
public class TweetRepositoryImpl implements TweetRepository {
    private final Map<Long, Tweet> tweets = new HashMap<>();

    @Override
    public Tweet save(Tweet tweet) {
        tweet.setId(Math.abs(new Random().nextLong()));
        tweets.put(tweet.getId(), tweet);
        return tweet;
    }

    @Override
    public List<Tweet> findAll() {
        return tweets.values().stream().toList();
    }

    @Override
    public Optional<Tweet> findById(Long id) {
        return Optional.ofNullable(tweets.get(id));
    }

    @Override
    public void deleteById(Long id) {
        tweets.remove(id);
    }
}