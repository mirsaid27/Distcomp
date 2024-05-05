package by.bsuir.discussion.service.interfaces;

import by.bsuir.discussion.exception.model.dto_convert.ToConvertException;
import by.bsuir.discussion.model.dto.note.NoteRequestTO;
import by.bsuir.discussion.model.dto.note.NoteResponseTO;
import by.bsuir.discussion.model.entity.implementations.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteToConverter extends ToConverter<Note, NoteRequestTO, NoteResponseTO> {

    @Mapping(target = "issue", ignore = true)
    Note convertToEntity(NoteRequestTO requestTo) throws ToConvertException;
}
