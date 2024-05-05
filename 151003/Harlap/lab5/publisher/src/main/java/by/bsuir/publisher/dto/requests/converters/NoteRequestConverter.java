package by.bsuir.publisher.dto.requests.converters;

import by.bsuir.publisher.domain.Note;
import by.bsuir.publisher.dto.requests.NoteRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteRequestConverter {
    Note fromDto(NoteRequestDto note);
}
