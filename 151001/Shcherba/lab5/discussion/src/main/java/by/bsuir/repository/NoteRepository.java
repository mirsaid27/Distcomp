package by.bsuir.repository;

import by.bsuir.entities.Note;
import by.bsuir.entities.NoteKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface NoteRepository extends CassandraRepository<Note, NoteKey> {
    List<Note> findByTweetId(int tweetId);
    List<Note> findById (int id);
    void deleteByCountryAndTweetIdAndId (String country, int tweetId, int id);
    int countByCountry (String country);
}
