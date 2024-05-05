package by.bsuir.repository;

import by.bsuir.entities.Note;
import by.bsuir.entities.NoteKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends CassandraRepository<Note, NoteKey> {
//    List<Note> findByStoryId(Long storyId);
//    List<Note> findById (Long id);
//    void deleteByCountryAndStoryIdAndId (String country, Long storyId, Long id);
//    int countByCountry (String country);
    @Query(allowFiltering = true)
    List<Note> findByKeyId(Long id);

//    @Query(allowFiltering = true)
//    List<Note> findByKeyStoryId(Long id);
//
//    @Query(allowFiltering = true)
//    void deleteByKeyCountryAndKeyStoryIdAndKeyId (String country, Long storyId, Long id);
//
    @Query(allowFiltering = true)
    int countByKeyCountry (String country);
}
