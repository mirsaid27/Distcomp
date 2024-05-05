package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Post;
import by.bsuir.discussion.dto.PostActionDto;
import by.bsuir.discussion.dto.PostActionTypeDto;
import by.bsuir.discussion.dto.requests.PostRequestDto;
import by.bsuir.discussion.dto.requests.converters.PostRequestConverter;
import by.bsuir.discussion.dto.responses.PostResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionPostResponseConverter;
import by.bsuir.discussion.dto.responses.converters.PostResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.ErrorDto;
import by.bsuir.discussion.exceptions.Messages;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.repositories.PostRepository;
import by.bsuir.discussion.services.PostService;
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
public class PostServiceImpl implements PostService {

    private final PostRepository messageRepository;
    private final PostRequestConverter messageRequestConverter;
    private final PostResponseConverter messageResponseConverter;
    private final CollectionPostResponseConverter collectionPostResponseConverter;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, PostActionDto> kafkaPostActionTemplate;

    @Value("${topic.messageChangeTopic}")
    private String messageChangeTopic;

    private PostService messageService;

    @Autowired
    public void setPostService(@Lazy PostService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "${topic.inTopic}")
    @SendTo
    protected PostActionDto receive(PostActionDto messageActionDto) {
        System.out.println("Received message: " + messageActionDto);
        switch (messageActionDto.getAction()) {
            case CREATE -> {
                try {
                    PostRequestDto messageRequest = objectMapper.convertValue(messageActionDto.getData(),
                            PostRequestDto.class);
                    return PostActionDto.builder().
                            action(PostActionTypeDto.CREATE).
                            data(messageService.create(messageRequest)).
                            build();
                } catch (EntityExistsException e) {
                    return PostActionDto.builder().
                            action(PostActionTypeDto.CREATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.EntityExistsException).
                                    build()).
                            build();
                }
            }
            case READ -> {
                Long id = Long.valueOf((String) messageActionDto.getData());
                return PostActionDto.builder().
                        action(PostActionTypeDto.READ).
                        data(messageService.read(id)).
                        build();
            }
            case READ_ALL -> {
                return PostActionDto.builder().
                        action(PostActionTypeDto.READ_ALL).
                        data(messageService.readAll()).
                        build();
            }
            case UPDATE -> {
                try {
                    PostRequestDto messageRequest = objectMapper.convertValue(messageActionDto.getData(),
                            PostRequestDto.class);
                    return PostActionDto.builder().
                            action(PostActionTypeDto.UPDATE).
                            data(messageService.update(messageRequest)).
                            build();
                } catch (NoEntityExistsException e) {
                    return PostActionDto.builder().
                            action(PostActionTypeDto.UPDATE).
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
                    return PostActionDto.builder().
                            action(PostActionTypeDto.DELETE).
                            data(messageService.delete(id)).
                            build();
                } catch (NoEntityExistsException e) {
                    return PostActionDto.builder().
                            action(PostActionTypeDto.DELETE).
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
    public PostResponseDto create(@Valid @NonNull PostRequestDto dto) throws EntityExistsException {
        Optional<Post> message = dto.getId() == null ? Optional.empty() : messageRepository.findPostById(dto.getId());
        if (message.isEmpty()) {
            Post entity = messageRequestConverter.fromDto(dto);
            if (dto.getId() == null) {
                entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            }
            PostResponseDto messageResponseDto = messageResponseConverter.toDto(messageRepository.save(entity));
            PostActionDto messageActionDto = PostActionDto.builder().
                    action(PostActionTypeDto.CREATE).
                    data(messageResponseDto).
                    build();
            ProducerRecord<String, PostActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                    System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                    messageActionDto);
            kafkaPostActionTemplate.send(record);
            return messageResponseDto;
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<PostResponseDto> read(@NonNull Long id) {
        return messageRepository.findPostById(id).flatMap(user -> Optional.of(
                messageResponseConverter.toDto(user)));
    }

    @Override
    @Validated
    public PostResponseDto update(@Valid @NonNull PostRequestDto dto) throws NoEntityExistsException {
        Optional<Post> message = dto.getId() == null || messageRepository.findPostByStoryIdAndId(
                dto.getStoryId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(messageRequestConverter.fromDto(dto));
        PostResponseDto messageResponseDto = messageResponseConverter.toDto(messageRepository.save(message.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
        PostActionDto messageActionDto = PostActionDto.builder().
                action(PostActionTypeDto.UPDATE).
                data(messageResponseDto).
                build();
        ProducerRecord<String, PostActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        kafkaPostActionTemplate.send(record);
        return messageResponseDto;
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Post> message = messageRepository.findPostById(id);
        messageRepository.deletePostByStoryIdAndId(message.map(Post::getStoryId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)), message.map(Post::getId).
                orElseThrow(() -> new NoEntityExistsException(Messages.NoEntityExistsException)));
        PostActionDto messageActionDto = PostActionDto.builder().
                action(PostActionTypeDto.DELETE).
                data(String.valueOf(id)).
                build();
        ProducerRecord<String, PostActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(messageActionDto.toString()),
                messageActionDto);
        kafkaPostActionTemplate.send(record);
        return message.get().getId();
    }

    @Override
    public List<PostResponseDto> readAll() {
        return collectionPostResponseConverter.toListDto(messageRepository.findAll());
    }
}
