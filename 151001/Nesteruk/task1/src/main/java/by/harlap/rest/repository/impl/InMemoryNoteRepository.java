package by.harlap.rest.repository.impl;

import by.harlap.rest.model.Note;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryNoteRepository extends AbstractInMemoryRepository<Note> {
}
