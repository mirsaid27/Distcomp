package by.bsuir.publisher.services.impl;

import by.bsuir.publisher.dto.CommentActionDto;
import by.bsuir.publisher.dto.CommentActionTypeDto;
import by.bsuir.publisher.dto.requests.CommentRequestDto;
import by.bsuir.publisher.dto.responses.ErrorDto;
import by.bsuir.publisher.dto.responses.CommentResponseDto;
import by.bsuir.publisher.exceptions.ServiceException;
import by.bsuir.publisher.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Validated
public class CommentServiceImpl implements CommentService {
    private final ObjectMapper objectMapper;

    private final ReplyingKafkaTemplate<String, CommentActionDto, CommentActionDto> replyingKafkaTemplate;

    @Value("${topic.inTopic}")
    private String inTopic;

    @Value("${topic.outTopic}")
    private String outTopic;

    protected CommentActionDto sendCommentAction(CommentActionDto commentActionDto) {
        ProducerRecord<String, CommentActionDto> record = new ProducerRecord<>(inTopic, null,
                System.currentTimeMillis(), String.valueOf(commentActionDto.toString()),
                commentActionDto);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, outTopic.getBytes()));
        RequestReplyFuture<String, CommentActionDto, CommentActionDto> response = replyingKafkaTemplate.sendAndReceive(record);
        return response.orTimeout(1000, TimeUnit.MILLISECONDS).join().value();
    }

    @Override
    public CommentResponseDto create(@NonNull CommentRequestDto dto) throws ServiceException {
        //Synchronous, as response in tests shall contain created comment id, and if passed id is null,
        //asynchronous messaging is not appropriate(we won't be able to obtain generated id)
        CommentActionDto action = sendCommentAction(CommentActionDto.builder().
                action(CommentActionTypeDto.CREATE).
                data(dto).
                build());
        CommentResponseDto response = objectMapper.convertValue(action.getData(), CommentResponseDto.class);
        if (ObjectUtils.allNull(response.getId(), response.getStoryId(), response.getContent())) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorDto.class));
        }
        return response;
    }

    @Override
    public Optional<CommentResponseDto> read(@NonNull Long uuid) {
        CommentActionDto action = sendCommentAction(CommentActionDto.builder().
                action(CommentActionTypeDto.READ).
                data(String.valueOf(uuid)).
                build());
        CommentResponseDto response = objectMapper.convertValue(action.getData(), CommentResponseDto.class);
        return Optional.ofNullable(response);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<CommentResponseDto> readAll() {
        return (List<CommentResponseDto>) ((List)sendCommentAction(CommentActionDto.builder().
                action(CommentActionTypeDto.READ_ALL).
                build()).getData()).stream().map(v -> objectMapper.convertValue(v, CommentResponseDto.class)).toList();
    }

    @Override
    public CommentResponseDto update(@NonNull CommentRequestDto dto) throws ServiceException {
        CommentActionDto action = sendCommentAction(CommentActionDto.builder().
                action(CommentActionTypeDto.UPDATE).
                data(dto).
                build());
        CommentResponseDto response = objectMapper.convertValue(action.getData(), CommentResponseDto.class);
        if (ObjectUtils.allNull(response.getId(), response.getContent(), response.getStoryId())) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorDto.class));
        }
        return response;
    }

    @Override
    public Long delete(@NonNull Long uuid) throws ServiceException {
        CommentActionDto action = sendCommentAction(CommentActionDto.builder().
                action(CommentActionTypeDto.DELETE).
                data(String.valueOf(uuid)).
                build());
        try {
            return Long.valueOf((Integer) action.getData());
        } catch (ClassCastException e) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorDto.class));
        }
    }
}
