package com.yankovich.dc_rest.repository.interfaces;

import com.yankovich.dc_rest.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TweetRepository extends JpaRepository <Tweet, Long> {
    boolean existsByTitle(String title);
}
