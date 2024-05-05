package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.MessageActionDto;
import by.bsuir.discussion.dto.MessageActionTypeDto;
import by.bsuir.discussion.dto.requests.NoteRequestDto;
import by.bsuir.discussion.dto.requests.converters.NoteRequestConverter;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionNoteResponseConverter;
import by.bsuir.discussion.dto.responses.converters.NoteResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.ErrorDto;
import by.bsuir.discussion.exceptions.Messages;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.repositories.NoteRepository;
import by.bsuir.discussion.services.NoteService;
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
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteRequestConverter noteRequestConverter;
    private final NoteResponseConverter noteResponseConverter;
    private final CollectionNoteResponseConverter collectionNoteResponseConverter;
    private final ObjectMapper objectMapper;

    private NoteService noteService;

    @Autowired
    public void setNoteService(@Lazy NoteService noteService) {
        this.noteService = noteService;
    }

    @KafkaListener(topics = "${topic.inTopic}")
    @SendTo
    protected MessageActionDto receive(MessageActionDto MessageActionDto) {
        System.out.println("Received note: " + MessageActionDto);
        switch (MessageActionDto.getAction()) {
            case CREATE -> {
                try {
                    NoteRequestDto noteRequest = objectMapper.convertValue(MessageActionDto.getData(),
                            NoteRequestDto.class);
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.CREATE).
                            data(noteService.create(noteRequest)).
                            build();
                } catch (EntityExistsException e) {
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.CREATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.EntityExistsException).
                                    build()).
                            build();
                }
            }
            case READ -> {
                Long id = Long.valueOf((String) MessageActionDto.getData());
                return MessageActionDto.builder().
                        action(MessageActionTypeDto.READ).
                        data(noteService.read(id)).
                        build();
            }
            case READ_ALL -> {
                return MessageActionDto.builder().
                        action(MessageActionTypeDto.READ_ALL).
                        data(noteService.readAll()).
                        build();
            }
            case UPDATE -> {
                try {
                    NoteRequestDto noteRequest = objectMapper.convertValue(MessageActionDto.getData(),
                            NoteRequestDto.class);
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.UPDATE).
                            data(noteService.update(noteRequest)).
                            build();
                } catch (NoEntityExistsException e) {
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.UPDATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.NoEntityExistsException).
                                    build()).
                            build();
                }
            }
            case DELETE -> {
                try {
                    Long id = Long.valueOf((String) MessageActionDto.getData());
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.DELETE).
                            data(noteService.delete(id)).
                            build();
                } catch (NoEntityExistsException e) {
                    return MessageActionDto.builder().
                            action(MessageActionTypeDto.DELETE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Messages.NoEntityExistsException).
                                    build()).
                            build();
                }
            }
        }
        return MessageActionDto;
    }

    @Override
    @Validated
    public NoteResponseDto create(@Valid @NonNull NoteRequestDto dto) throws EntityExistsException {
        Optional<Note> note = dto.getId() == null ? Optional.empty() : noteRepository.findNoteById(dto.getId());
        if (note.isEmpty()) {
            Note entity = noteRequestConverter.fromDto(dto);
            if (dto.getId() == null) {
                entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            }
            return noteResponseConverter.toDto(noteRepository.save(entity));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<NoteResponseDto> read(@NonNull Long id) {
        return noteRepository.findNoteById(id).flatMap(author -> Optional.of(
                noteResponseConverter.toDto(author)));
    }

    @Override
    @Validated
    public NoteResponseDto update(@Valid @NonNull NoteRequestDto dto) throws NoEntityExistsException {
        Optional<Note> note = dto.getId() == null || noteRepository.findNoteBytweetIdAndId(
                dto.getTweetId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(noteRequestConverter.fromDto(dto));
        return noteResponseConverter.toDto(noteRepository.save(note.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Note> note = noteRepository.findNoteById(id);
        noteRepository.deleteNoteBytweetIdAndId(note.map(Note::getTweetId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)), note.map(Note::getId).
                orElseThrow(() -> new NoEntityExistsException(Messages.NoEntityExistsException)));
        return note.get().getId();
    }

    @Override
    public List<NoteResponseDto> readAll() {
        return collectionNoteResponseConverter.toListDto(noteRepository.findAll());
    }
}
