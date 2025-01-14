package com.rmakovetskij.dc_rest.mapper;

import com.rmakovetskij.dc_rest.model.Editor;
import com.rmakovetskij.dc_rest.model.dto.requests.EditorRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.EditorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")  // Важно указать componentModel = "spring"
public interface EditorMapper {

    EditorResponseTo toResponse(Editor editor);

    @Mapping(target = "id", ignore = true)  // Игнорируем поле id, так как оно генерируется
    Editor toEntity(EditorRequestTo editorRequestDto);
}