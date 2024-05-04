package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.NoteActionDto;
import by.bsuir.discussion.dto.NoteActionTypeDto;
import by.bsuir.discussion.dto.requests.NoteRequestDto;
import by.bsuir.discussion.dto.requests.converters.NoteRequestConverter;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionNoteResponseConverter;
import by.bsuir.discussion.dto.responses.converters.NoteResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.ErrorDto;
import by.bsuir.discussion.exceptions.Notes;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.repositories.NoteRepository;
import by.bsuir.discussion.services.NoteService;
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
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteRequestConverter noteRequestConverter;
    private final NoteResponseConverter noteResponseConverter;
    private final CollectionNoteResponseConverter collectionNoteResponseConverter;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, NoteActionDto> kafkaNoteActionTemplate;

    @Value("${topic.messageChangeTopic}")
    private String messageChangeTopic;

    private NoteService noteService;

    @Autowired
    public void setNoteService(@Lazy NoteService noteService) {
        this.noteService = noteService;
    }

    @KafkaListener(topics = "${topic.inTopic}")
    @SendTo
    protected NoteActionDto receive(NoteActionDto noteActionDto) {
        System.out.println("Received note: " + noteActionDto);
        switch (noteActionDto.getAction()) {
            case CREATE -> {
                try {
                    NoteRequestDto noteRequest = objectMapper.convertValue(noteActionDto.getData(),
                            NoteRequestDto.class);
                    return NoteActionDto.builder().
                            action(NoteActionTypeDto.CREATE).
                            data(noteService.create(noteRequest)).
                            build();
                } catch (EntityExistsException e) {
                    return NoteActionDto.builder().
                            action(NoteActionTypeDto.CREATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Notes.EntityExistsException).
                                    build()).
                            build();
                }
            }
            case READ -> {
                Long id = Long.valueOf((String) noteActionDto.getData());
                return NoteActionDto.builder().
                        action(NoteActionTypeDto.READ).
                        data(noteService.read(id)).
                        build();
            }
            case READ_ALL -> {
                return NoteActionDto.builder().
                        action(NoteActionTypeDto.READ_ALL).
                        data(noteService.readAll()).
                        build();
            }
            case UPDATE -> {
                try {
                    NoteRequestDto noteRequest = objectMapper.convertValue(noteActionDto.getData(),
                            NoteRequestDto.class);
                    return NoteActionDto.builder().
                            action(NoteActionTypeDto.UPDATE).
                            data(noteService.update(noteRequest)).
                            build();
                } catch (NoEntityExistsException e) {
                    return NoteActionDto.builder().
                            action(NoteActionTypeDto.UPDATE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Notes.NoEntityExistsException).
                                    build()).
                            build();
                }
            }
            case DELETE -> {
                try {
                    Long id = Long.valueOf((String) noteActionDto.getData());
                    return NoteActionDto.builder().
                            action(NoteActionTypeDto.DELETE).
                            data(noteService.delete(id)).
                            build();
                } catch (NoEntityExistsException e) {
                    return NoteActionDto.builder().
                            action(NoteActionTypeDto.DELETE).
                            data(ErrorDto.builder().
                                    code(HttpStatus.BAD_REQUEST.value() + "00").
                                    message(Notes.NoEntityExistsException).
                                    build()).
                            build();
                }
            }
        }
        return noteActionDto;
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
            NoteResponseDto noteResponseDto = noteResponseConverter.toDto(noteRepository.save(entity));
            NoteActionDto noteActionDto = NoteActionDto.builder().
                    action(NoteActionTypeDto.CREATE).
                    data(noteResponseDto).
                    build();
            ProducerRecord<String, NoteActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                    System.currentTimeMillis(), String.valueOf(noteActionDto.toString()),
                    noteActionDto);
            kafkaNoteActionTemplate.send(record);
            return noteResponseDto;
        } else {
            throw new EntityExistsException(Notes.EntityExistsException);
        }
    }

    @Override
    public Optional<NoteResponseDto> read(@NonNull Long id) {
        return noteRepository.findNoteById(id).flatMap(user -> Optional.of(
                noteResponseConverter.toDto(user)));
    }

    @Override
    @Validated
    public NoteResponseDto update(@Valid @NonNull NoteRequestDto dto) throws NoEntityExistsException {
        Optional<Note> note = dto.getId() == null || noteRepository.findNoteByTweetIdAndId(
                dto.getTweetId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(noteRequestConverter.fromDto(dto));
        NoteResponseDto noteResponseDto = noteResponseConverter.toDto(noteRepository.save(note.orElseThrow(() ->
                new NoEntityExistsException(Notes.NoEntityExistsException))));
        NoteActionDto noteActionDto = NoteActionDto.builder().
                action(NoteActionTypeDto.UPDATE).
                data(noteResponseDto).
                build();
        ProducerRecord<String, NoteActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(noteActionDto.toString()),
                noteActionDto);
        kafkaNoteActionTemplate.send(record);
        return noteResponseDto;
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Note> note = noteRepository.findNoteById(id);
        noteRepository.deleteNoteByTweetIdAndId(note.map(Note::getTweetId).orElseThrow(() ->
                new NoEntityExistsException(Notes.NoEntityExistsException)), note.map(Note::getId).
                orElseThrow(() -> new NoEntityExistsException(Notes.NoEntityExistsException)));
        NoteActionDto noteActionDto = NoteActionDto.builder().
                action(NoteActionTypeDto.DELETE).
                data(String.valueOf(id)).
                build();
        ProducerRecord<String, NoteActionDto> record = new ProducerRecord<>(messageChangeTopic, null,
                System.currentTimeMillis(), String.valueOf(noteActionDto.toString()),
                noteActionDto);
        kafkaNoteActionTemplate.send(record);
        return note.get().getId();
    }

    @Override
    public List<NoteResponseDto> readAll() {
        return collectionNoteResponseConverter.toListDto(noteRepository.findAll());
    }
}
