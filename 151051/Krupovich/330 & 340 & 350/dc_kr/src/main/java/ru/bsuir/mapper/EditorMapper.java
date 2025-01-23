package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.entity.Editor;

@Mapper(componentModel = "spring")
public interface EditorMapper {

    @Mapping(target = "id", ignore = true)
    Editor toEntity(EditorRequestTo editorRequest);

    EditorResponseTo toDTO(Editor editor);
}