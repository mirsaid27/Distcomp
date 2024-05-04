package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateEditorDto;
import by.bsuir.poit.dc.rest.api.dto.response.EditorDto;
import by.bsuir.poit.dc.rest.model.Editor;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class EditorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tweets", ignore = true)
    public abstract Editor toEntity(UpdateEditorDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tweets", ignore = true)
    public abstract Editor partialUpdate(
	@MappingTarget Editor editor,
	UpdateEditorDto dto);

    @Named("toDto")
    public abstract EditorDto toDto(Editor editor);

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<EditorDto> toDtoList(List<Editor> editors);
}
