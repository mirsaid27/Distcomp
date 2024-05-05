package by.bsuir.services;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import by.bsuir.exceptions.DeleteException2;
import by.bsuir.exceptions.NotFoundException2;
import by.bsuir.exceptions.UpdateException2;
import by.bsuir.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Service
@Validated
public class NoteService {
    @Autowired
    NoteRepository noteDao;

    public NoteResponseTo getNoteById(Long id) throws NotFoundException2 {
        Optional<Note> note = noteDao.findByKeyId(id).stream().findFirst();
        return note.map(this::noteToNoteResponse).orElseThrow(() -> new NotFoundException2("Note not found!", 40004L));
    }

    public List<NoteResponseTo> getNotes() {
        return toNoteResponseList(noteDao.findAll());
    }

    public NoteResponseTo saveNote(@Valid NoteRequestTo note, String country) throws MethodArgumentNotValidException {
        Note noteToSave = noteRequestToNote(note);
        noteToSave.getKey().setId(getId());
        noteToSave.getKey().setCountry(getCountry(country));
        return noteToNoteResponse(noteDao.save(noteToSave));
    }

    public void deleteNote(Long id) throws DeleteException2 {
        Optional<Note> note = noteDao.findByKeyId(id).stream().findFirst();
        if (note.isEmpty()) {
            throw new DeleteException2("Note not found!", 40004L);
        } else {
            noteDao.deleteById(note.get().getKey());
        }
    }

    public NoteResponseTo updateNote(@Valid NoteRequestTo note, String country) throws UpdateException2 {
        Note noteToUpdate = noteRequestToNote(note);
        noteToUpdate.getKey().setId(note.getId());
        noteToUpdate.getKey().setCountry(getCountry(country));
        Optional<Note> noteOptional = noteDao.findByKeyId(note.getId()).stream().findFirst();
        if (noteOptional.isEmpty()) {
            throw new UpdateException2("Note not found!", 40004L);
        } else {
            //noteDao.deleteByKeyCountryAndKeyStoryIdAndKeyId(noteOptional.get().getKey().getCountry(), noteOptional.get().getKey().getStoryId(), noteOptional.get().getKey().getId());
            noteDao.deleteById(noteOptional.get().getKey());
            return noteToNoteResponse(noteDao.save(noteToUpdate));
        }
    }

//    public List<NoteResponseTo> getNoteByStoryId(Long storyId) throws NotFoundException2 {
//        List<Note> note = noteDao.findByKeyStoryId(storyId);
//        if (note.isEmpty()) {
//            throw new NotFoundException2("Note not found!", 40004L);
//        }
//        return toNoteResponseList(note);
//    }

    private NoteResponseTo noteToNoteResponse(Note note) {
        NoteResponseTo noteResponseTo = new NoteResponseTo();
        noteResponseTo.setStoryId(note.getKey().getStoryId());
        noteResponseTo.setId(note.getKey().getId());
        noteResponseTo.setContent(note.getContent());
        return noteResponseTo;
    }

    private List<NoteResponseTo> toNoteResponseList(List<Note> notes) {
        List<NoteResponseTo> response = new ArrayList<>();
        for (Note note : notes) {
            response.add(noteToNoteResponse(note));
        }
        return response;
    }

    private Note noteRequestToNote(NoteRequestTo requestTo) {
        Note note = new Note();
        note.getKey().setId(requestTo.getId());
        note.setContent(requestTo.getContent());
        note.getKey().setStoryId(requestTo.getStoryId());
        return note;
    }

    private long getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, noteDao.countByKeyCountry(country)*(1-languageMap.get(country)));
        }
        double minValue = Double.MAX_VALUE;
        String minKey = null;

        for (Map.Entry<String, Double> entry : loadMap.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    private static Map<String, Double> getStringDoubleMap(String requestHeader) {
        String[] languages = requestHeader.split(",");
        Map<String, Double> languageMap = new HashMap<>();
        for (String language : languages) {
            String[] parts = language.split(";");
            String lang = parts[0].trim();
            double priority = 1.0; // По умолчанию
            if (parts.length > 1) {
                String[] priorityParts = parts[1].split("=");
                priority = Double.parseDouble(priorityParts[1]);
            }
            languageMap.put(lang, priority);
        }
        return languageMap;
    }
}
