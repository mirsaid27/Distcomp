package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.model.entity.Note;
import by.bsuir.discussion.model.request.NoteRequestTo;
import by.bsuir.discussion.model.response.NoteResponseTo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    @Mapping(target = "id", source = "key.id")
    @Mapping(target = "newsId", source = "key.newsId")
    NoteResponseTo getResponseTo(Note note);

    List<NoteResponseTo> getListResponseTo(Iterable<Note> notes);

    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.newsId", source = "newsId")
    Note getNote(NoteRequestTo noteRequestTo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.newsId", source = "newsId")
    Note partialUpdate(NoteRequestTo noteRequestTo, @MappingTarget Note note);
}
