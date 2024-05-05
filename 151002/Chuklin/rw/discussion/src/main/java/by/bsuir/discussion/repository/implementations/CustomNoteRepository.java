package by.bsuir.discussion.repository.implementations;

import by.bsuir.discussion.model.entity.implementations.Note;
import by.bsuir.discussion.repository.interfaces.NoteRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomNoteRepository extends CassandraRepository<Note, Long>, NoteRepository {

}
