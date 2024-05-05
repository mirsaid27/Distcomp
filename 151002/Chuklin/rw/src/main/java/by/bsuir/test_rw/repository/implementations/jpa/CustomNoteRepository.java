package by.bsuir.test_rw.repository.implementations.jpa;

import by.bsuir.test_rw.model.entity.implementations.Note;
import by.bsuir.test_rw.repository.interfaces.NoteRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomNoteRepository extends JpaRepository<Note, Long>, NoteRepository {
}
