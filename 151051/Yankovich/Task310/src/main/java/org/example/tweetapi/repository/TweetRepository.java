package org.example.tweetapi.repository;

import org.example.tweetapi.model.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
