package com.example.distributedcomputing.repository;

import com.example.distributedcomputing.model.entity.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    Optional<Tweet> findByTitle(String title);
}
