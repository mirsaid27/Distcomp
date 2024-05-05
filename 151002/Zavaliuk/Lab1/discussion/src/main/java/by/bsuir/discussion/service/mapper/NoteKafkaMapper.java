package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.model.entity.Note;
import by.bsuir.discussion.model.response.NoteResponseTo;
import by.bsuir.discussion.event.NoteInTopicTo;
import by.bsuir.discussion.event.NoteOutTopicTo;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteKafkaMapper {

    NoteOutTopicTo responseDtoToOutTopicDto(NoteResponseTo noteResponseTo);

    List<NoteOutTopicTo> responseDtoToOutTopicDto(Collection<NoteResponseTo> noteResponseTo);

    @Mapping(target = "key", source = ".")
    Note toEntity(NoteInTopicTo dto);

    @Mapping(target = ".", source = "key")
    NoteOutTopicTo entityToDto(Note entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "key.newsId", source = "newsId")
    Note partialUpdate(NoteInTopicTo noteInTopicTo, @MappingTarget Note note);
}