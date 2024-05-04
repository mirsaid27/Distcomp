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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostRequestConverter postRequestConverter;
    private final PostResponseConverter postResponseConverter;
    private final CollectionPostResponseConverter collectionPostResponseConverter;
    private final ObjectMapper objectMapper;

    private PostService postService;

    @Autowired
    public void setPostService(@Lazy PostService postService) {
        this.postService = postService;
    }

    @KafkaListener(topics = "${topic.inTopic}")
    @SendTo
    protected PostActionDto receive(PostActionDto postActionDto) {
        System.out.println("Received post: " + postActionDto);
        switch (postActionDto.getAction()) {
            case CREATE -> {
                try {
                    PostRequestDto postRequest = objectMapper.convertValue(postActionDto.getData(),
                            PostRequestDto.class);
                    return PostActionDto.builder().
                            action(PostActionTypeDto.CREATE).
                            data(postService.create(postRequest)).
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
                Long id = Long.valueOf((String) postActionDto.getData());
                return PostActionDto.builder().
                        action(PostActionTypeDto.READ).
                        data(postService.read(id)).
                        build();
            }
            case READ_ALL -> {
                return PostActionDto.builder().
                        action(PostActionTypeDto.READ_ALL).
                        data(postService.readAll()).
                        build();
            }
            case UPDATE -> {
                try {
                    PostRequestDto postRequest = objectMapper.convertValue(postActionDto.getData(),
                            PostRequestDto.class);
                    return PostActionDto.builder().
                            action(PostActionTypeDto.UPDATE).
                            data(postService.update(postRequest)).
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
                    Long id = Long.valueOf((String) postActionDto.getData());
                    return PostActionDto.builder().
                            action(PostActionTypeDto.DELETE).
                            data(postService.delete(id)).
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
        return postActionDto;
    }

    @Override
    @Validated
    public PostResponseDto create(@Valid @NonNull PostRequestDto dto) throws EntityExistsException {
        Optional<Post> post = dto.getId() == null ? Optional.empty() : postRepository.findPostById(dto.getId());
        if (post.isEmpty()) {
            Post entity = postRequestConverter.fromDto(dto);
            if (dto.getId() == null) {
                entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            }
            return postResponseConverter.toDto(postRepository.save(entity));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<PostResponseDto> read(@NonNull Long id) {
        return postRepository.findPostById(id).flatMap(user -> Optional.of(
                postResponseConverter.toDto(user)));
    }

    @Override
    @Validated
    public PostResponseDto update(@Valid @NonNull PostRequestDto dto) throws NoEntityExistsException {
        Optional<Post> post = dto.getId() == null || postRepository.findPostByStoryIdAndId(
                dto.getStoryId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(postRequestConverter.fromDto(dto));
        return postResponseConverter.toDto(postRepository.save(post.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Post> post = postRepository.findPostById(id);
        postRepository.deletePostByStoryIdAndId(post.map(Post::getStoryId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)), post.map(Post::getId).
                orElseThrow(() -> new NoEntityExistsException(Messages.NoEntityExistsException)));
        return post.get().getId();
    }

    @Override
    public List<PostResponseDto> readAll() {
        return collectionPostResponseConverter.toListDto(postRepository.findAll());
    }
}
