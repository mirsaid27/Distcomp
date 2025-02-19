package by.kopvzakone.distcomp.dto;

import by.kopvzakone.distcomp.entities.Editor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EditorMapper {

    EditorResponseTo out(Editor editor);

    Editor in(EditorRequestTo editor);
}
