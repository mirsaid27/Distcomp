package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Comment;
import by.bsuir.discussion.dto.CommentActionDto;
import by.bsuir.discussion.dto.CommentActionTypeDto;
import by.bsuir.discussion.dto.requests.CommentRequestDto;
import by.bsuir.discussion.dto.requests.converters.CommentRequestConverter;
import by.bsuir.discussion.dto.responses.CommentResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionCommentResponseConverter;
import by.bsuir.discussion.dto.responses.converters.CommentResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.ErrorDto;
import by.bsuir.discussion.exceptions.Messages;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.repositories.CommentRepository;
import by.bsuir.discussion.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(rollbackFor = {EntityExistsException.class, NoEntityExistsException.class})
public class CommentServiceImpl implements CommentService {

    private final CommentRepository messageRepository;
    private final CommentRequestConverter messageRequestConverter;
    private final CommentResponseConverter messageResponseConverter;
    private final CollectionCommentResponseConverter collectionCommentResponseConverter;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, CommentActionDto> kafkaCommentActionTemplate;

    @Value("${topic.messageChangeTopic}")
    private String messageChangeTopic;

    private CommentService messageService;

    @Autowired
    public void setCommentService(@Lazy CommentService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "${topic.inTopic}")
    @SendTo
    protected CommentActionDto receive(CommentActionDto messageActionDto) {
        System.out.println("Received message: " + messageActionDto);
        switch (messageActionDto.getAction()) {
            case CREATE -> {
                try {
                    CommentRequestDto messageRequest = objectMapper.convertValue(messageActionDto.getData(),
                            CommentRequestDto.class);
                    return CommentActionDto.builder().
                            action(CommentActionTypeDto.CREATE).
                            data(messageService.create(messageRequest)).
                            build();
                } catch (EntityExistsException e) {
                    return CommentActionDto.builder().
                            action(CommentActionTypeDto.CREATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.EntityExistsException).
                                    build()).
                            build();
                }
            }
            case READ -> {
                Long id = Long.valueOf((String) messageActionDto.getData());
                return CommentActionDto.builder().
                        action(CommentActionTypeDto.READ).
                        data(messageService.read(id)).
                        build();
            }
            case READ_ALL -> {
                return CommentActionDto.builder().
                        action(CommentActionTypeDto.READ_ALL).
                        data(messageService.readAll()).
                        build();
            }
            case UPDATE -> {
                try {
                    CommentRequestDto messageRequest = objectMapper.convertValue(messageActionDto.getData(),
                            CommentRequestDto.class);
                    return CommentActionDto.builder().
                            action(CommentActionTypeDto.UPDATE).
                            data(messageService.update(messageRequest)).
                            build();
                } catch (NoEntityExistsException e) {
                    return CommentActionDto.builder().
                            action(CommentActionTypeDto.UPDATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.NoEntityExistsException).
                                    build()).
                            build();
                }
            }
            case DELETE -> {
                try {
                    Long id = Long.valueOf((String) messageActionDto.getData());
                    return CommentActionDto.builder().
                            action(CommentActionTypeDto.DELETE).
                            data(messageService.delete(id)).
                            build();
                } catch (NoEntityExistsException e) {
                    return CommentActionDto.builder().
                            action(CommentActionTypeDto.DELETE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.NoEntityExistsException).
                                    build()).
                            build();
                }
            }
        }
        return messageActionDto;
    }

    @Override
    @Validated
    public CommentResponseDto create(@Valid @NonNull CommentRequestDto dto) throws EntityExistsException {
        Optional<Comment> message = dto.getId() == null ? Optional.empty() : messageRepository.findCommentById(dto.getId());
        if (message.isEmpty()) {
            Comment entity = messageRequestConverter.fromDto(dto);
            if (dto.getId() == null) {
                entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            }
            CommentResponseDto messageResponseDto = messageResponseConverter.toDto(messageRepository.save(entity));
            CommentActionDto messageActionDto = CommentActionDto.builder().
                    action(CommentActionTypeDto.CREATE).
                    data(messageResponseDto).
                    build();
            ProducerRecord<String, CommentActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                    System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                    messageActionDto);
            kafkaCommentActionTemplate.send(record);
            return messageResponseDto;
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<CommentResponseDto> read(@NonNull Long id) {
        return messageRepository.findCommentById(id).flatMap(user -> Optional.of(
                messageResponseConverter.toDto(user)));
    }

    @Override
    @Validated
    public CommentResponseDto update(@Valid @NonNull CommentRequestDto dto) throws NoEntityExistsException {
        Optional<Comment> message = dto.getId() == null || messageRepository.findCommentByStoryIdAndId(
                dto.getStoryId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(messageRequestConverter.fromDto(dto));
        CommentResponseDto messageResponseDto = messageResponseConverter.toDto(messageRepository.save(message.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
        CommentActionDto messageActionDto = CommentActionDto.builder().
                action(CommentActionTypeDto.UPDATE).
                data(messageResponseDto).
                build();
        ProducerRecord<String, CommentActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        kafkaCommentActionTemplate.send(record);
        return messageResponseDto;
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Comment> message = messageRepository.findCommentById(id);
        messageRepository.deleteCommentByStoryIdAndId(message.map(Comment::getStoryId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)), message.map(Comment::getId).
                orElseThrow(() -> new NoEntityExistsException(Messages.NoEntityExistsException)));
        CommentActionDto messageActionDto = CommentActionDto.builder().
                action(CommentActionTypeDto.DELETE).
                data(String.valueOf(id)).
                build();
        ProducerRecord<String, CommentActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        kafkaCommentActionTemplate.send(record);
        return message.get().getId();
    }

    @Override
    public List<CommentResponseDto> readAll() {
        return collectionCommentResponseConverter.toListDto(messageRepository.findAll());
    }
}
