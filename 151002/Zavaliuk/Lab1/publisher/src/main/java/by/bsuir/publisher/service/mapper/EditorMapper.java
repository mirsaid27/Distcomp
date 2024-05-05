package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Editor;
import by.bsuir.publisher.model.request.EditorRequestTo;
import by.bsuir.publisher.model.response.EditorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EditorMapper {
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    EditorResponseTo getResponseTo(Editor editor);

    List<EditorResponseTo> getListResponseTo(Iterable<Editor> editors);

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    Editor getEditor(EditorRequestTo editorRequestTo);
}
