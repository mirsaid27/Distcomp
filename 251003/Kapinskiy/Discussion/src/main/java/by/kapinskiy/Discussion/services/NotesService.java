package by.kapinskiy.Discussion.services;


import by.kapinskiy.Discussion.DTOs.Requests.NoteRequestDTO;
import by.kapinskiy.Discussion.DTOs.Responses.NoteResponseDTO;
import by.kapinskiy.Discussion.models.Note;
import by.kapinskiy.Discussion.repositories.NotesRepository;
import by.kapinskiy.Discussion.utils.mappers.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class NotesService {
    private final NotesRepository notesRepository;
    private final NotesMapper notesMapper;
    @Value("${note.country}")
    private String country;

    @Autowired
    public NotesService(NotesRepository notesRepository, NotesMapper notesMapper) {
        this.notesRepository = notesRepository;
        this.notesMapper = notesMapper;
    }

    public NoteResponseDTO save(NoteRequestDTO noteRequestDTO) {
        Note note = notesMapper.toNote(noteRequestDTO);
        note.getKey().setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        return notesMapper.toNoteResponse(notesRepository.save(note));
    }

    public List<NoteResponseDTO> findAll() {
        return notesMapper.toNoteResponseList(notesRepository.findAll());
    }

    public NoteResponseDTO findById(Long id) {
        return notesMapper.toNoteResponse(
                notesRepository.findByCountryAndId(country, id)
                        .orElseThrow(() -> new RuntimeException("Note not found"))
        );
    }

    public void deleteById(long id) {
        notesRepository.deleteByCountryAndId(country, id);
    }

    public NoteResponseDTO update(NoteRequestDTO noteRequestDTO) {
        Note note = notesMapper.toNote(noteRequestDTO);
        note.getKey().setId(noteRequestDTO.getId());
        return notesMapper.toNoteResponse(notesRepository.save(note));
    }

}
