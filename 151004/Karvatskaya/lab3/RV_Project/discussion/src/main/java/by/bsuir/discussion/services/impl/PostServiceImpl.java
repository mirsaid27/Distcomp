package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Post;
import by.bsuir.discussion.dto.requests.PostRequestDto;
import by.bsuir.discussion.dto.requests.converters.PostRequestConverter;
import by.bsuir.discussion.dto.responses.PostResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionPostResponseConverter;
import by.bsuir.discussion.dto.responses.converters.PostResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.Messages;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.repositories.PostRepository;
import by.bsuir.discussion.services.PostService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    @Override
    @Validated
    public PostResponseDto create(@Valid @NonNull PostRequestDto dto) throws EntityExistsException {
        Optional<Post> post = dto.getId() == null ? Optional.empty() : postRepository.findPostById(dto.getId());
        if (post.isEmpty()) {
            Post entity = postRequestConverter.fromDto(dto);
            entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            return postResponseConverter.toDto(postRepository.save(entity));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<PostResponseDto> read(@NonNull Long id) {
        return postRepository.findPostById(id).flatMap(creator -> Optional.of(
                postResponseConverter.toDto(creator)));
    }

    @Override
    @Validated
    public PostResponseDto update(@Valid @NonNull PostRequestDto dto) throws NoEntityExistsException {
        Optional<Post> post = dto.getId() == null || postRepository.findPostByIssueIdAndId(
                dto.getIssueId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(postRequestConverter.fromDto(dto));
        return postResponseConverter.toDto(postRepository.save(post.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Post> post = postRepository.findPostById(id);
        postRepository.deletePostByIssueIdAndId(post.map(Post::getIssueId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)), post.map(Post::getId).
                orElseThrow(() -> new NoEntityExistsException(Messages.NoEntityExistsException)));
        return post.get().getId();
    }

    @Override
    public List<PostResponseDto> readAll() {
        return collectionPostResponseConverter.toListDto(postRepository.findAll());
    }
}
