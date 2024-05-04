package by.bsuir.discussion.services.impl;

import by.bsuir.discussion.domain.Note;
import by.bsuir.discussion.dto.requests.NoteRequestDto;
import by.bsuir.discussion.dto.requests.converters.NoteRequestConverter;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import by.bsuir.discussion.dto.responses.converters.CollectionNoteResponseConverter;
import by.bsuir.discussion.dto.responses.converters.NoteResponseConverter;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.Notes;
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

    private final NoteRepository noteRepository;
    private final NoteRequestConverter noteRequestConverter;
    private final NoteResponseConverter noteResponseConverter;
    private final CollectionNoteResponseConverter collectionNoteResponseConverter;
    @Override
    @Validated
    public NoteResponseDto create(@Valid @NonNull NoteRequestDto dto) throws EntityExistsException {
        Optional<Note> note = dto.getId() == null ? Optional.empty() : noteRepository.findNoteById(dto.getId());
        if (note.isEmpty()) {
            Note entity = noteRequestConverter.fromDto(dto);
            entity.setId((long) (Math.random() * 2_000_000_000L) + 1);
            return noteResponseConverter.toDto(noteRepository.save(entity));
        } else {
            throw new EntityExistsException(Notes.EntityExistsException);
        }
    }

    @Override
    public Optional<NoteResponseDto> read(@NonNull Long id) {
        return noteRepository.findNoteById(id).flatMap(editor -> Optional.of(
                noteResponseConverter.toDto(editor)));
    }

    @Override
    @Validated
    public NoteResponseDto update(@Valid @NonNull NoteRequestDto dto) throws NoEntityExistsException {
        Optional<Note> note = dto.getId() == null || noteRepository.findNoteByTweetIdAndId(
                dto.getTweetId(), dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(noteRequestConverter.fromDto(dto));
        return noteResponseConverter.toDto(noteRepository.save(note.orElseThrow(() ->
                new NoEntityExistsException(Notes.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Note> note = noteRepository.findNoteById(id);
        noteRepository.deleteNoteByTweetIdAndId(note.map(Note::getTweetId).orElseThrow(() ->
                new NoEntityExistsException(Notes.NoEntityExistsException)), note.map(Note::getId).
                orElseThrow(() -> new NoEntityExistsException(Notes.NoEntityExistsException)));
        return note.get().getId();
    }

    @Override
    public List<NoteResponseDto> readAll() {
        return collectionNoteResponseConverter.toListDto(noteRepository.findAll());
    }
}
