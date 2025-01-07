package com.example.mapper;

import com.example.dto.EditorRequestTo;
import com.example.dto.EditorResponseTo;
import com.example.entities.Editor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EditorMapper.class)
public interface EditorListMapper {
    List<Editor> toEditorList(List<EditorRequestTo> editors);
    List<EditorResponseTo> toEditorResponseList(List<Editor> editors);
}
