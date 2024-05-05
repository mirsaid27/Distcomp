package by.bsuir.discussion.dto.requests.converters;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.requests.NoteRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteRequestConverter {
    Note fromDto(NoteRequestDto note);
}
