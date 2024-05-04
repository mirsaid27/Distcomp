package by.harlap.jpa.mapper;

import by.harlap.jpa.dto.request.CreateNoteDto;
import by.harlap.jpa.dto.request.UpdateNoteDto;
import by.harlap.jpa.dto.response.NoteResponseDto;
import by.harlap.jpa.model.Note;
import org.mapstruct.*;

@Mapper
public interface NoteMapper {

    @Mapping(target = "tweet.id", source = "tweetId")
    Note toNote(CreateNoteDto noteRequest);

    @Mapping(target="tweetId", source="tweet.id")
    NoteResponseDto toNoteResponse(Note note);

    @Mapping(target = "tweet.id", source = "tweetId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Note toNote(UpdateNoteDto noteRequest, @MappingTarget Note note);
}
