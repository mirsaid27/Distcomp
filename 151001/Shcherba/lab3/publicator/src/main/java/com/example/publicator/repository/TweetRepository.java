package com.example.publicator.repository;

import com.example.publicator.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TweetRepository extends JpaRepository<Tweet,Integer> {

    Page<Tweet> findAll (Pageable pageable);
    boolean existsByTitle(String title);

}


