package com.example.discussion.repository;

import com.example.discussion.model.Note;
import com.example.discussion.model.NoteKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;


public interface NoteRepository extends CassandraRepository<Note, NoteKey> {
    List<Note> findAll();
    void deleteByCountryAndTweetIdAndId(String country, int tweetId, int id);
    List<Note> findById (int id);

    Double countByCountry(String country);
}
