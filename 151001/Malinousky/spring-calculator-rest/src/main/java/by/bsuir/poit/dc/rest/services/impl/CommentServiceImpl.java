package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.LanguageQualityParser;
import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.kafka.dto.KafkaUpdateCommentDto;
import by.bsuir.poit.dc.kafka.dto.CommentRequest;
import by.bsuir.poit.dc.kafka.dto.CommentResponse;
import by.bsuir.poit.dc.kafka.dto.RequestEvent;
import by.bsuir.poit.dc.kafka.service.AbstractReactiveKafkaService;
import by.bsuir.poit.dc.rest.api.dto.mappers.CommentMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.rest.api.dto.response.CommentDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.services.TweetsService;
import by.bsuir.poit.dc.rest.services.CommentService;
import com.google.errorprone.annotations.Keep;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static by.bsuir.poit.dc.LanguageQualityParser.OrderType;

/**
 * @author Name Surname
 * 
 */
@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@CacheConfig(cacheNames = "commentCache")
@RequiredArgsConstructor
public class CommentServiceImpl extends AbstractReactiveKafkaService<CommentResponse> implements CommentService {
    private final TweetsService tweetsService;
    private final LanguageQualityParser parser;
    private final CommentMapper commentMapper;
    @Value("${location.default}")
    @Setter
    private String defaultCountry;
    @Value("${publisher.topics.target}")
    @Setter
    private String targetTopic;
    private final KafkaTemplate<UUID, CommentRequest> kafkaTemplate;

    @KafkaListener(topics = "${publisher.topics.source}")
    public void processIncoming(ConsumerRecord<UUID, CommentResponse> response) {
	log.trace("Ready to process incoming record: {}\n", response);
	super.consumerResponse(response);
    }

    private Pair<UUID, Mono<CommentResponse>> send(CommentRequest request) {
	var key = super.nextSessionId();
	var mono = super.nextMonoResponse(key);//bind key for response
	kafkaTemplate.send(targetTopic, key, request);
	return Pair.of(key, mono);
    }

    @Deprecated
    private CommentResponse sendBlocking(CommentRequest request) {
	Pair<UUID, Mono<CommentResponse>> pair = send(request);
	var key = pair.getFirst();
	var mono = pair.getSecond();
	CommentResponse response = mono.block(Duration.ofSeconds(1));
	if (response == null) {
	    throw newResourceBusyException(key, "Time is up!");
	}
	return response;
    }

    @PostConstruct
    public void init() {
	if (defaultCountry == null) {
	    final String msg = "The default country is not provided";
	    log.error(msg);
	    throw new IllegalStateException(msg);
	}
	if (targetTopic == null) {
	    final String msg = "The target topic is not specified";
	    log.error(msg);
	    throw new IllegalStateException(msg);
	}
    }

    @Override
    public CommentDto getById(long commentId) {
	var request = CommentRequest.builder()
			  .event(RequestEvent.FIND_BY_ID)
			  .id(commentId).dto(null)
			  .build();
	CommentResponse response = sendBlocking(request);
	return commentMapper.unwrapResponse(response.list().getFirst());
    }

    @Override
    public List<CommentDto> getAllByTweetId(long tweetId) {
	var request = CommentRequest.withId(RequestEvent.FIND_BY_TWEET_ID, tweetId);
	CommentResponse response = sendBlocking(request);
	return commentMapper.unwrapResponseList(response.list());
    }

    @Override
    @Deprecated
    public List<CommentDto> getAll() {
	var request = CommentRequest.builder()
			  .id(null).dto(null)
			  .event(RequestEvent.FIND_ALL)
			  .build();
	CommentResponse response = sendBlocking(request);
	return commentMapper.unwrapResponseList(response.list());
    }

    @Override
    public CommentDto save(UpdateCommentDto dto, long tweetId, @Nullable String language) {
	final List<String> countries = fetchCountries(language);
	KafkaUpdateCommentDto kafkaDto = commentMapper.buildRequest(dto, countries);
	var request = CommentRequest.builder()
			  .event(RequestEvent.CREATE)
			  .id(null).dto(kafkaDto)
			  .build();
	CommentResponse response = sendBlocking(request);
	return commentMapper.unwrapResponse(response.list().getFirst());
    }

    @Override
    public CommentDto update(long commentId, UpdateCommentDto dto, @Nullable String language) {
	final List<String> countries = fetchCountries(language);
	KafkaUpdateCommentDto kafkaDto = commentMapper.buildRequest(dto, countries);
	var request = CommentRequest.builder()
			  .event(RequestEvent.UPDATE)
			  .id(commentId).dto(kafkaDto)
			  .build();
	CommentResponse response = sendBlocking(request);
	return commentMapper.unwrapResponse(response.list().getFirst());
    }


    @Override
    public PresenceDto delete(long commentId) {
	var request = CommentRequest.builder()
			  .event(RequestEvent.DELETE_BY_ID)
			  .id(commentId).dto(null)
			  .build();
	CommentResponse response = sendBlocking(request);
	boolean isDeleted = switch (response.status()) {
	    case OK -> true;
	    default -> false;
	};
	return PresenceDto.wrap(isDeleted);
    }

    private List<String> fetchCountries(@Nullable String language) {
	return Optional.ofNullable(language)
		   .flatMap(lang -> parser.parse(lang, OrderType.PREFERABLE))
		   .orElseGet(this::defaultCountries);
    }

    @Keep
    private static ResourceNotFoundException newTweetNotFoundException(long tweetId) {
	final String msg = STR."Any interaction with comment is forbidden because corresponding tweet is not present. Tweet id = \{tweetId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 142);

    }

    @Keep
    private static ResourceBusyException newCommentCreationException(long newsId, KafkaUpdateCommentDto dto) {
	final String msg = STR."Failed to create comment for tweet by id = \{newsId} for dto = \{dto}";
	log.warn(msg);
	return new ResourceBusyException(msg, 143);

    }

    private List<String> defaultCountries() {
	return List.of(defaultCountry);
    }

    @Override
    protected Throwable newServerBusyException(UUID sessionId) {
	return newResourceBusyException(sessionId, "Server is busy");
    }

    @Override
    protected Throwable newEntityNotFoundException(UUID sessionId) {
	final String msg = "Related comment or tweet is not found";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 147);

    }

    @Override
    protected Throwable newBadRequestException(UUID sessionId) {
	final String msg = "Failed save comment> Bad request format";
	log.warn(msg);
	return new ResourceModifyingException(msg, 146);

    }

    @Keep
    private static ResourceBusyException newResourceBusyException(UUID sessionId, String reason) {
	final String msg = STR."Failed to access resource with session id = \{sessionId} by reason: \{reason}";
	log.warn(msg);
	throw new ResourceBusyException(reason, 144);
    }
}
