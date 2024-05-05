package by.bsuir.discussion.dto.responses.converters;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoteResponseConverter.class)
public interface CollectionNoteResponseConverter {
    List<NoteResponseDto> toListDto(List<Note> messages);
}
