package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Note;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteResponseConverter {
    NoteResponseDto toDto(Note note);
    Note fromDto(NoteResponseDto noteResponseDto);
}
