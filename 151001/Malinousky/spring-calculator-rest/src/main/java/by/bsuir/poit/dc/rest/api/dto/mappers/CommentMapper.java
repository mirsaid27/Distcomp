package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.kafka.dto.*;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.rest.api.dto.response.CommentDto;
import by.bsuir.poit.dc.rest.dao.TweetRepository;
import by.bsuir.poit.dc.rest.model.Tweet;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CommentMapper {
    @Autowired
    private TweetRepository tweetRepository;

    public abstract KafkaUpdateCommentDto buildRequest(
	UpdateCommentDto dto,
	List<String> countries);

    public abstract CommentDto unwrapResponse(KafkaCommentDto dto);

    public abstract List<CommentDto> unwrapResponseList(List<KafkaCommentDto> list);

    @Named("getTweetRef")
    protected Tweet getTweetRef(long tweetId) {
	return tweetRepository.getReferenceById(tweetId);
    }
}
