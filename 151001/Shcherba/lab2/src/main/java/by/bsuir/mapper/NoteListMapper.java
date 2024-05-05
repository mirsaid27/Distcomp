package by.bsuir.mapper;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoteMapper.class)
public interface NoteListMapper {
    List<Note> toNoteList(List<NoteRequestTo> notes);
    List<NoteResponseTo> toNoteResponseList(List<Note> notes);
}
