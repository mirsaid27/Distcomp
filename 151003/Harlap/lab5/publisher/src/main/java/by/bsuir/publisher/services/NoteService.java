package by.bsuir.publisher.services;

import by.bsuir.publisher.dto.requests.NoteRequestDto;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import by.bsuir.publisher.exceptions.ServiceException;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    NoteResponseDto create(@Valid @NonNull NoteRequestDto dto) throws ServiceException;
    Optional<NoteResponseDto> read(@NonNull Long uuid);
    List<NoteResponseDto> readAll();
    NoteResponseDto update(@Valid @NonNull NoteRequestDto dto) throws ServiceException;
    Long delete(@NonNull Long uuid) throws ServiceException;
}
