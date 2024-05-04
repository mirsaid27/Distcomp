package by.bsuir.publisher.services;

import by.bsuir.publisher.dto.requests.PostRequestDto;
import by.bsuir.publisher.dto.responses.PostResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostResponseDto create(@Valid @NonNull PostRequestDto dto) throws EntityExistsException;
    Optional<PostResponseDto> read(@NonNull Long uuid);
    List<PostResponseDto> readAll();
    PostResponseDto update(@Valid @NonNull PostRequestDto dto) throws NoEntityExistsException;
    Long delete(@NonNull Long uuid) throws NoEntityExistsException;
}
