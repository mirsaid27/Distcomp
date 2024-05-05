package by.bsuir.services;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class NoteService {
    @Autowired
    NoteRepository noteDao;

    public NoteResponseTo getNoteById(int id) throws NotFoundException {
        Optional<Note> note = noteDao.findById(id).stream().findFirst();
        return note.map(this::noteToNoteResponse).orElseThrow(() -> new NotFoundException("Note not found!", 40004));
    }

    public List<NoteResponseTo> getNotes() {
        return toNoteResponseList(noteDao.findAll());
    }

    public NoteResponseTo saveNote(@Valid NoteRequestTo note, String country) {
        Note noteToSave = noteRequestToNote(note);
        noteToSave.setId(getId());
        noteToSave.setCountry(getCountry(country));
        return noteToNoteResponse(noteDao.save(noteToSave));
    }

    public void deleteNote(int id) throws DeleteException {
        Optional<Note> note = noteDao.findById(id).stream().findFirst();
        if (note.isEmpty()) {
            throw new DeleteException("Note not found!", 40004);
        } else {
            noteDao.deleteByCountryAndTweetIdAndId(note.get().getCountry(), note.get().getTweetId(), note.get().getId());
        }
    }

    public NoteResponseTo updateNote(@Valid NoteRequestTo note, String country) throws UpdateException {
        Note noteToUpdate = noteRequestToNote(note);
        noteToUpdate.setId(note.getId());
        noteToUpdate.setCountry(getCountry(country));
        Optional<Note> noteOptional = noteDao.findById(note.getId()).stream().findFirst();
        if (noteOptional.isEmpty()) {
            throw new UpdateException("Note not found!", 40004);
        } else {
            noteDao.deleteByCountryAndTweetIdAndId(noteOptional.get().getCountry(), noteOptional.get().getTweetId(), noteOptional.get().getId());
            return noteToNoteResponse(noteDao.save(noteToUpdate));
        }
    }

    public List<NoteResponseTo> getNoteByTweetId(int tweetId) throws NotFoundException {
        List<Note> note = noteDao.findByTweetId(tweetId);
        if (note.isEmpty()) {
            throw new NotFoundException("Note not found!", 40004);
        }
        return toNoteResponseList(note);
    }

    private NoteResponseTo noteToNoteResponse(Note note) {
        NoteResponseTo noteResponseTo = new NoteResponseTo();
        noteResponseTo.setTweetId(note.getTweetId());
        noteResponseTo.setId(note.getId());
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
        note.setId(requestTo.getId());
        note.setContent(requestTo.getContent());
        note.setTweetId(requestTo.getTweetId());
        return note;
    }

    private int getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, noteDao.countByCountry(country)*(1-languageMap.get(country)));
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
