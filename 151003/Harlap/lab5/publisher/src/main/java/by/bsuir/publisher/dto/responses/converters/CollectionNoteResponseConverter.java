package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Note;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoteResponseConverter.class)
public interface CollectionNoteResponseConverter {
    List<NoteResponseDto> toListDto(List<Note> notes);
}
