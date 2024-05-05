package by.bsuir.egor.dto;

import by.bsuir.egor.Entity.Editor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EditorMapper {
    EditorMapper INSTANCE = Mappers.getMapper( EditorMapper.class );



    EditorResponseTo editorToEditorResponseTo(Editor editor);

    EditorRequestTo editorToEditorRequestTo(Editor editor);

    Editor editorResponseToToEditor(EditorResponseTo editorResponseTo);

    Editor editorRequestToToEditor(EditorRequestTo editorRequestTo);
}
