package com.example.mapper;

import com.example.api.dto.EditorRequestTo;
import com.example.api.dto.EditorResponseTo;
import com.example.entities.Editor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditorMapper {
    Editor editorRequestToEditor(EditorRequestTo editorRequestTo);

    EditorResponseTo editorToEditorResponse(Editor editor);
}