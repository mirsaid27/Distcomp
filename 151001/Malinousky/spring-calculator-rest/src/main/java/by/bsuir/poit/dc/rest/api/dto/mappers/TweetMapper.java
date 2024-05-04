package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetDto;
import by.bsuir.poit.dc.rest.api.dto.response.TweetDto;
import by.bsuir.poit.dc.rest.api.dto.response.EditorDto;
import by.bsuir.poit.dc.rest.model.Tweet;
import by.bsuir.poit.dc.rest.model.Editor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class)
public abstract class TweetMapper {
    @Autowired
    private EditorMapper editorMapper;

    @Mapping(target = "stickers", ignore = true)
    @Mapping(target = "editor", source = "editorId", qualifiedByName = "getUserRef")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    public abstract Tweet toEntity(UpdateTweetDto dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "created", source = "created", qualifiedByName = "mapDate")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "mapDate")
    @Named("toDto")
    public abstract TweetDto toDto(Tweet tweet);

    @Mapping(target = "stickers", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "editor",
	source = "editorId",
	qualifiedByName = "getEditorRef")
    public abstract Tweet partialUpdate(
	@MappingTarget Tweet tweet,
	UpdateTweetDto dto);


    @Named("mapUser")
    protected EditorDto mapUser(Editor editor) {
	return editorMapper.toDto(editor);
    }

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<TweetDto> toDtoList(List<Tweet> list);
}
