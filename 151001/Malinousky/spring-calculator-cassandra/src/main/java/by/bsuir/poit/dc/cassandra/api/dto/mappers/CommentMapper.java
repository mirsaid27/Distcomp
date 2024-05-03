package by.bsuir.poit.dc.cassandra.api.dto.mappers;

import by.bsuir.poit.dc.cassandra.api.dto.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.CommentDto;
import by.bsuir.poit.dc.cassandra.model.CommentById;
import by.bsuir.poit.dc.cassandra.model.CommentByTweet;
import by.bsuir.poit.dc.kafka.dto.KafkaCommentDto;
import by.bsuir.poit.dc.kafka.dto.KafkaCommentMapper;
import by.bsuir.poit.dc.kafka.dto.KafkaUpdateCommentDto;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class
)
public abstract class CommentMapper implements KafkaCommentMapper<UpdateCommentDto, CommentDto> {
    @Mapping(target = "status", ignore = true)
    public abstract UpdateCommentDto unwrapRequest(KafkaUpdateCommentDto dto);

    public abstract KafkaCommentDto buildResponse(CommentDto dto);

    public abstract List<KafkaCommentDto> buildRequestList(List<CommentDto> dto);

    @Mapping(target = "id", source = "id")
    public abstract CommentById toEntityById(long id, UpdateCommentDto dto);

    @Mapping(target = "id", source = "id")
    public abstract CommentByTweet toEntityByTweet(long id, UpdateCommentDto dto);


    public abstract CommentById partialUpdate(
	@MappingTarget CommentById comment,
	UpdateCommentDto dto);

    public abstract CommentByTweet partialUpdate(
	@MappingTarget CommentByTweet comment,
	UpdateCommentDto dto
    );

    public abstract CommentDto toDto(CommentById comment);

    public abstract CommentDto toDto(CommentByTweet commentByTweet);

    public abstract List<CommentDto> toDtoList(List<CommentById> list);

    public abstract List<CommentDto> toDtoListFromCommentByTweet(List<CommentByTweet> list);
}
