package by.bsuir.mapper;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note noteRequestToNote(NoteRequestTo noteRequestTo);

    @Mapping(target = "tweetId", source = "note.tweet.id")
    NoteResponseTo noteToNoteResponse(Note note);
}
