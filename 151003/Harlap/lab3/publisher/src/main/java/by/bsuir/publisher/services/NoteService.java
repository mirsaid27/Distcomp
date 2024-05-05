package by.bsuir.publisher.services;

import by.bsuir.publisher.dto.requests.NoteRequestDto;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    NoteResponseDto create(@Valid @NonNull NoteRequestDto dto) throws EntityExistsException;
    Optional<NoteResponseDto> read(@NonNull Long uuid);
    List<NoteResponseDto> readAll();
    NoteResponseDto update(@Valid @NonNull NoteRequestDto dto) throws NoEntityExistsException;
    Long delete(@NonNull Long uuid) throws NoEntityExistsException;
}
