package by.bsuir.dao.impl;

import by.bsuir.dao.NoteDao;
import by.bsuir.entities.Note;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class NoteDaoImpl implements NoteDao {
    private long counter = 0;
    private final Map<Long, Note> map = new HashMap<>();

    @Override
    public Note save(Note entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null){
            throw new DeleteException("The Note has not been deleted", 40003L);
        }
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Note> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Note update(Note updatedNote) throws UpdateException {
        Long id = updatedNote.getId();

        if (map.containsKey(id)) {
            Note existingNote = map.get(id);
            BeanUtils.copyProperties(updatedNote, existingNote);
            return existingNote;
        } else {
            throw new UpdateException("Note update failed", 40002L);
        }
    }
}
