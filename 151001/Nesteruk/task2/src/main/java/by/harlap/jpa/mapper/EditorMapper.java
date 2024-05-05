package by.harlap.jpa.mapper;

import by.harlap.jpa.dto.request.CreateEditorDto;
import by.harlap.jpa.dto.request.UpdateEditorDto;
import by.harlap.jpa.dto.response.EditorResponseDto;
import by.harlap.jpa.model.Editor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface EditorMapper {

    Editor toEditor(CreateEditorDto editorRequest);

    EditorResponseDto toEditorResponse(Editor editor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Editor toEditor(UpdateEditorDto editorRequest, @MappingTarget Editor editor);
}
