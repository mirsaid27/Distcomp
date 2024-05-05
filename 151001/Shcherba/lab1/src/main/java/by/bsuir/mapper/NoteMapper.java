package by.bsuir.mapper;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note NoteRequestToNote(NoteRequestTo NoteRequestTo);

    NoteResponseTo NoteToNoteResponse(Note Note);
}
