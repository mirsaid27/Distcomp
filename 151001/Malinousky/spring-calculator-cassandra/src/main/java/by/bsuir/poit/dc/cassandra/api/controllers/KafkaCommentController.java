package by.bsuir.poit.dc.cassandra.api.controllers;

import by.bsuir.poit.dc.cassandra.api.dto.mappers.CommentMapper;
import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.CommentDto;
import by.bsuir.poit.dc.cassandra.api.exceptions.ContentNotValidException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.cassandra.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.cassandra.services.CommentService;
import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.kafka.dto.*;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * @author Name Surname
 * 
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class KafkaCommentController {
    @Value("${discussion.topics.target}")
    @Setter
    private String targetTopicName;
    private final CommentService commentService;
    private final KafkaTemplate<UUID, CommentResponse> kafkaTemplate;
    private final CommentMapper commentMapper;
    private final Map<RequestEvent, BiFunction<CommentRequest, UpdateCommentDto, CommentResponse>> handlers = Map.of(
	RequestEvent.CREATE, this::createTweetComment,
	RequestEvent.UPDATE, this::updateCommentById,
	RequestEvent.DELETE_BY_ID, this::deleteCommentById,
	RequestEvent.FIND_BY_ID, this::getCommentById,
	RequestEvent.FIND_BY_TWEET_ID, this::getCommentByTweetId,
	RequestEvent.FIND_ALL, this::getAllComments
    );

    @Deprecated
    public CommentResponse getAllComments(
	CommentRequest request,
	@Null UpdateCommentDto dto
    ) {
	List<CommentDto> list = commentService.getAll();
	List<KafkaCommentDto> kafkaList = commentMapper.buildRequestList(list);
	return CommentResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(kafkaList)
		   .build();
    }

    public CommentResponse createTweetComment(
	CommentRequest request,
	@Validated(Create.class) UpdateCommentDto dto) {
	CommentDto response = commentService.save(dto);
	KafkaCommentDto kafkaDto = commentMapper.buildResponse(response);
	return CommentResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(List.of(kafkaDto))
		   .build();
    }

    public CommentResponse getCommentByTweetId(
	CommentRequest request,
	@Null UpdateCommentDto _dto) {
	if (request.id() == null) {
	    return CommentResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long tweetId = request.id();
	List<CommentDto> list = commentService.getAllByTweetId(tweetId);
	List<KafkaCommentDto> kafkaList = commentMapper.buildRequestList(list);
	assert list != null;
	return CommentResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(kafkaList)
		   .build();
    }

    public CommentResponse getCommentById(
	CommentRequest request,
	@Null UpdateCommentDto _dto) {
	if (request.id() == null) {
	    return CommentResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long commentId = request.id();
	CommentDto response = commentService.getById(commentId);
	KafkaCommentDto kafkaDto = commentMapper.buildResponse(response);
	return CommentResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(List.of(kafkaDto))
		   .build();
    }

    public CommentResponse updateCommentById(
	CommentRequest request,
	@Validated(Update.class) UpdateCommentDto dto) {
	if (request.id() == null) {
	    return CommentResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long commentId = request.id();
	CommentDto response = commentService.update(commentId, dto);
	KafkaCommentDto kafkaDto = commentMapper.buildResponse(response);
	return CommentResponse.builder()
		   .status(ResponseEvent.OK)
		   .list(List.of(kafkaDto))
		   .build();
    }

    public CommentResponse deleteCommentById(
	CommentRequest request,
	@Null UpdateCommentDto _dto) {
	if (request.id() == null) {
	    return CommentResponse.withStatus(ResponseEvent.INVALID_FORMAT);
	}
	long commentId = request.id();
	var status = commentService.delete(commentId).isPresent()
			 ? ResponseEvent.OK
			 : ResponseEvent.NOT_FOUND;
	return CommentResponse.withStatus(status);
    }

    @KafkaListener(topics = "${discussion.topics.source}")
    public void processCommentRequest(ConsumerRecord<UUID, CommentRequest> record) {
	UUID key = record.key();
	CommentRequest request = record.value();
	if (key == null || request == null) {
	    log.error(STR."Failed to process request with key=\{key} and value=\{request}");
	    return;
	}
	CommentResponse response;
	try {
	    RequestEvent event = request.event();
	    UpdateCommentDto dto = commentMapper.unwrapRequest(request.dto());
	    var function = handlers.get(event);
	    assert function != null : "Event handler cannot be null";
	    response = function.apply(request, dto);
	} catch (Throwable t) {
	    log.error(STR."Exception is caught = \{t.getMessage()}");
	    Throwable cause = t.getCause() != null ? t.getCause() : t;
	    ResponseEvent status = switch (cause) {
		case MethodArgumentNotValidException _,
			 ResourceModifyingException _,
			 ContentNotValidException _ -> ResponseEvent.INVALID_FORMAT;
		case ResourceNotFoundException _ -> ResponseEvent.NOT_FOUND;
		default -> ResponseEvent.BUSY;
	    };
	    response = CommentResponse.withStatus(status);
	}
	kafkaTemplate.send(targetTopicName, key, response);
    }
}
