package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.requests.NoteRequestDto;
import by.bsuir.discussion.dto.requests.converters.NoteRequestConverter;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionNoteResponseConverter;
import by.bsuir.discussion.dto.responses.converters.NoteResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.Messages;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.repositories.NoteRepository;
import by.bsuir.discussion.services.NoteService;
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
public class NoteServiceImpl implements NoteService {

    private final NoteRepository messageRepository;
    private final NoteRequestConverter messageRequestConverter;
    private final NoteResponseConverter messageResponseConverter;
    private final CollectionNoteResponseConverter collectionNoteResponseConverter;
    @Override
    @Validated
    public NoteResponseDto create(@Valid @NonNull NoteRequestDto dto) throws EntityExistsException {
        Optional<Note> message = dto.getId() == null ? Optional.empty() : messageRepository.findNoteById(dto.getId());
        if (message.isEmpty()) {
            Note entity = messageRequestConverter.fromDto(dto);
            entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            return messageResponseConverter.toDto(messageRepository.save(entity));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<NoteResponseDto> read(@NonNull Long id) {
        return messageRepository.findNoteById(id).flatMap(author -> Optional.of(
                messageResponseConverter.toDto(author)));
    }

    @Override
    @Validated
    public NoteResponseDto update(@Valid @NonNull NoteRequestDto dto) throws NoEntityExistsException {
        Optional<Note> message = dto.getId() == null || messageRepository.findNoteByTweetIdAndId(
                dto.getTweetId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(messageRequestConverter.fromDto(dto));
        return messageResponseConverter.toDto(messageRepository.save(message.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Note> message = messageRepository.findNoteById(id);
        messageRepository.deleteNoteByTweetIdAndId(message.map(Note::getTweetId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)), message.map(Note::getId).
                orElseThrow(() -> new NoEntityExistsException(Messages.NoEntityExistsException)));
        return message.get().getId();
    }

    @Override
    public List<NoteResponseDto> readAll() {
        return collectionNoteResponseConverter.toListDto(messageRepository.findAll());
    }
}
