package com.example.publisherservice.repository;

import com.example.publisherservice.model.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StoryRepository extends CrudRepository<Story, Long> {

    Optional<Story> findByTitle(String title);
}
