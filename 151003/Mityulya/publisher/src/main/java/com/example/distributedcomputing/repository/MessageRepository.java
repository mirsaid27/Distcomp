package com.example.distributedcomputing.repository;

import com.example.distributedcomputing.model.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    Optional<Message> findByTweetId(Long id);
}
