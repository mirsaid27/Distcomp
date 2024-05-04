package by.harlap.rest.service;

import by.harlap.rest.model.Tweet;

import java.util.List;

public interface TweetService {

    Tweet findById(Long id);

    void deleteById(Long id);

    Tweet save(Tweet tweet);

    Tweet update(Tweet tweet);

    List<Tweet> findAll();
}
