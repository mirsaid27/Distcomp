package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetStickerDto;
import by.bsuir.poit.dc.rest.model.TweetSticker;
import by.bsuir.poit.dc.rest.model.TweetStickerId;
import org.mapstruct.*;

/**
 * @author Name Surname
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class
)
public abstract class TweetStickerMapper {
    @Mapping(target = "id", expression = "java(mapId(tweetId, dto.stickerId()))")
    @Mapping(target = "tweet", source = "tweetId", qualifiedByName = "getTweetRef")
    @Mapping(target = "sticker", source = "dto.stickerId", qualifiedByName = "getStickerRef")
    public abstract TweetSticker toEntity(long tweetId, UpdateTweetStickerDto dto);

    @Named("mapId")
    protected TweetStickerId mapId(long tweetId, long labelId) {
	return TweetStickerId.builder()
		   .tweetId(tweetId)
		   .labelId(labelId)
		   .build();
    }

}
