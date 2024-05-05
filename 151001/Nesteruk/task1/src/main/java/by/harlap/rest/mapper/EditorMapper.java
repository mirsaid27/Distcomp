package by.harlap.rest.mapper;

import by.harlap.rest.dto.request.CreateEditorDto;
import by.harlap.rest.dto.request.UpdateEditorDto;
import by.harlap.rest.dto.response.EditorResponseDto;
import by.harlap.rest.model.Editor;
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
