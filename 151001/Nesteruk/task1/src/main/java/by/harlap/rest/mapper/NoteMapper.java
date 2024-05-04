package by.harlap.rest.mapper;

import by.harlap.rest.dto.request.CreateNoteDto;
import by.harlap.rest.dto.request.UpdateNoteDto;
import by.harlap.rest.dto.response.NoteResponseDto;
import by.harlap.rest.model.Note;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface NoteMapper {
    Note toNote(CreateNoteDto noteRequest);

    NoteResponseDto toNoteResponse(Note note);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Note toNote(UpdateNoteDto noteRequest, @MappingTarget Note note);
}
