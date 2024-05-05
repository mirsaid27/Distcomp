package by.bsuir.discussion.dto.responses.converters;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteResponseConverter {
    NoteResponseDto toDto(Note note);
}
