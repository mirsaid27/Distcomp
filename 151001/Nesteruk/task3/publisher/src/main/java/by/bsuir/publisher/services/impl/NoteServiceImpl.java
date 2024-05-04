package by.bsuir.publisher.services.impl;

import by.bsuir.publisher.dto.requests.NoteRequestDto;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.Messages;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.services.NoteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class NoteServiceImpl implements NoteService {
    private final RestTemplate restTemplate;
    private final static String CREATE_MESSAGE_URI = "http://localhost:24130/api/v1.0/notes";
    private final static String READ_MESSAGE_URI = "http://localhost:24130/api/v1.0/notes/";
    private final static String READ_ALL_MESSAGE_URI = "http://localhost:24130/api/v1.0/notes";
    private final static String UPDATE_MESSAGE_URI = "http://localhost:24130/api/v1.0/notes";
    private final static String DELETE_MESSAGE_URI = "http://localhost:24130/api/v1.0/notes/";
    @Override
    public NoteResponseDto create(@NonNull NoteRequestDto dto) throws EntityExistsException {
        try {
            return restTemplate.postForObject(CREATE_MESSAGE_URI, dto, NoteResponseDto.class);
        } catch (HttpClientErrorException e) {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<NoteResponseDto> read(@NonNull Long uuid) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(READ_MESSAGE_URI + uuid,
                    NoteResponseDto.class));
        } catch(HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NoteResponseDto> readAll() {
        return restTemplate.getForObject(READ_ALL_MESSAGE_URI, List.class);
    }

    @Override
    public NoteResponseDto update(@NonNull NoteRequestDto dto) throws NoEntityExistsException {
        try {
            return restTemplate.exchange(UPDATE_MESSAGE_URI, HttpMethod.PUT,
                    new HttpEntity<>(dto), NoteResponseDto.class).getBody();
        } catch (HttpClientErrorException e) {
            throw new NoEntityExistsException(Messages.NoEntityExistsException);
        }
    }

    @Override
    public Long delete(@NonNull Long uuid) throws NoEntityExistsException {
        try {
            return restTemplate.exchange(DELETE_MESSAGE_URI + uuid, HttpMethod.DELETE,
                    new HttpEntity<>(uuid), Long.class).getBody();
        }
         catch (HttpClientErrorException e) {
            throw new NoEntityExistsException(Messages.NoEntityExistsException);
        }
    }
}
