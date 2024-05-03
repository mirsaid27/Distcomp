package com.example.publisherservice.repository;

import com.example.publisherservice.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
