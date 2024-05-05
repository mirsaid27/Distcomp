package by.bsuir.publisher.services.impl;

import by.bsuir.publisher.dto.PostActionDto;
import by.bsuir.publisher.dto.PostActionTypeDto;
import by.bsuir.publisher.dto.requests.PostRequestDto;
import by.bsuir.publisher.dto.responses.ErrorDto;
import by.bsuir.publisher.dto.responses.PostResponseDto;
import by.bsuir.publisher.dto.responses.converters.PostResponseConverter;
import by.bsuir.publisher.exceptions.ServiceException;
import by.bsuir.publisher.repositories.PostCacheRepository;
import by.bsuir.publisher.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(rollbackFor = {ServiceException.class})
public class PostServiceImpl implements PostService {
    private final ObjectMapper objectMapper;
    private final PostResponseConverter postResponseConverter;
    private final PostCacheRepository postCacheRepository;

    private final ReplyingKafkaTemplate<String, PostActionDto, PostActionDto> replyingKafkaTemplate;

    @Value("${topic.inTopic}")
    private String inTopic;

    @Value("${topic.outTopic}")
    private String outTopic;

    @KafkaListener(topics = "${topic.postChangeTopic}")
    protected void receivePostChange(PostActionDto postActionDto) {
        switch (postActionDto.getAction()) {
            case CREATE, UPDATE -> {
                PostResponseDto postResponseDto = objectMapper.
                        convertValue(postActionDto.getData(), PostResponseDto.class);
                postCacheRepository.save(postResponseConverter.fromDto(postResponseDto));
            }
            case DELETE -> {
                Long id = Long.valueOf((String) postActionDto.getData());
                postCacheRepository.deleteById(id);
            }
        }
    }

    protected PostActionDto sendPostAction(PostActionDto postActionDto) {
        ProducerRecord<String, PostActionDto> record = new ProducerRecord<>(inTopic, null,
                System.currentTimeMillis(), String.valueOf(postActionDto.toString()),
                postActionDto);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, outTopic.getBytes()));
        RequestReplyFuture<String, PostActionDto, PostActionDto> response = replyingKafkaTemplate.sendAndReceive(record);
        return response.orTimeout(1000, TimeUnit.MILLISECONDS).join().value();
    }

    @Override
    public PostResponseDto create(@NonNull PostRequestDto dto) throws ServiceException {
        //Synchronous, as response in tests shall contain created post id, and if passed id is null,
        //asynchronous messaging is not appropriate(we won't be able to obtain generated id)
        PostActionDto action = sendPostAction(PostActionDto.builder().
                action(PostActionTypeDto.CREATE).
                data(dto).
                build());
        PostResponseDto response = objectMapper.convertValue(action.getData(), PostResponseDto.class);
        if (ObjectUtils.allNull(response.getId(), response.getStoryId(), response.getContent())) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorDto.class));
        }
        postCacheRepository.save(postResponseConverter.fromDto(response));
        return response;
    }

    @Override
    public Optional<PostResponseDto> read(@NonNull Long uuid) {
        return Optional.ofNullable(postCacheRepository.findById(uuid).map(postResponseConverter::toDto).orElseGet(() -> {
            PostActionDto action = sendPostAction(PostActionDto.builder().
                    action(PostActionTypeDto.READ).
                    data(String.valueOf(uuid)).
                    build());
            return objectMapper.convertValue(action.getData(), PostResponseDto.class);
        }));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<PostResponseDto> readAll() {
        return (List<PostResponseDto>) ((List)sendPostAction(PostActionDto.builder().
                action(PostActionTypeDto.READ_ALL).
                build()).getData()).stream().map(v -> objectMapper.convertValue(v, PostResponseDto.class)).toList();
    }

    @Override
    public PostResponseDto update(@NonNull PostRequestDto dto) throws ServiceException {
        PostActionDto action = sendPostAction(PostActionDto.builder().
                action(PostActionTypeDto.UPDATE).
                data(dto).
                build());
        PostResponseDto response = objectMapper.convertValue(action.getData(), PostResponseDto.class);
        if (ObjectUtils.allNull(response.getId(), response.getContent(), response.getStoryId())) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorDto.class));
        }
        postCacheRepository.save(postResponseConverter.fromDto(response));
        return response;
    }

    @Override
    public Long delete(@NonNull Long uuid) throws ServiceException {
        PostActionDto action = sendPostAction(PostActionDto.builder().
                action(PostActionTypeDto.DELETE).
                data(String.valueOf(uuid)).
                build());
        try {
            postCacheRepository.deleteById(uuid);
            return Long.valueOf((Integer) action.getData());
        } catch (ClassCastException e) {
            throw new ServiceException(objectMapper.convertValue(action.getData(), ErrorDto.class));
        }
    }
}
