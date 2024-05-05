package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.model.Sticker;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class StickerMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Sticker toEntity(UpdateStickerDto dto);

    @Mapping(target = "id", ignore = true)
    public abstract Sticker partialUpdate(
	@MappingTarget Sticker sticker,
	UpdateStickerDto dto);

    @Named("toDto")
    public abstract StickerDto toDto(Sticker sticker);

    @IterableMapping(qualifiedByName = "toDto")
    public abstract List<StickerDto> toDtoList(List<Sticker> stickers);
}
