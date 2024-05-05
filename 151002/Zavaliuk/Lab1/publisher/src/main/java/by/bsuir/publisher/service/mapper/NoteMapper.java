package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.request.NoteRequestTo;
import by.bsuir.publisher.model.response.NoteResponseTo;
import by.bsuir.publisher.event.NoteInTopicTo;
import by.bsuir.publisher.event.NoteOutTopicTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponseTo toDto(NoteOutTopicTo messageOutTopicEvent);

    List<NoteResponseTo> toDto(Collection<NoteOutTopicTo> noteOutTopicTo);

    @Mapping(target = "country", ignore = true)
    NoteInTopicTo toInTopicDto(NoteRequestTo noteResponseTo);

    List<NoteInTopicTo> toInTopicDto(Collection<NoteRequestTo> noteRequestTos);
}