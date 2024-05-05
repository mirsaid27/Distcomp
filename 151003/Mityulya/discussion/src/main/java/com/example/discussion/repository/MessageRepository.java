package com.example.discussion.repository;

import com.example.discussion.model.entity.Message;
import com.example.discussion.model.entity.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    Optional<Message> findById (Long id);
    int countByCountry(String country);

    void deleteByCountryAndTweetIdAndId (String country, Long storyId, Long id);
}
