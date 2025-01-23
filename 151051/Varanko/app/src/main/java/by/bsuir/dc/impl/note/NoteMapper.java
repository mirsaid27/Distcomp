package by.bsuir.dc.impl.note;

import by.bsuir.dc.impl.note.model.Note;
import by.bsuir.dc.impl.note.model.NoteRequest;
import by.bsuir.dc.impl.note.model.NoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface NoteMapper {
    NoteResponse toNoteResponseDto(Note note);
    List<NoteResponse> toNoteResponseDto(Iterable<Note> note);
    List<NoteResponse> toNoteRequestDto(Iterable<Note> note);
    NoteRequest toNoteRequestDto(Note note);
    Note toNoteDto(NoteRequest request);
    List<Note> toNoteDto(Iterable<NoteRequest> requests);
}
