package com.example.discussion.service;


import com.example.discussion.exceptions.DeleteException;
import com.example.discussion.exceptions.NotFoundException;
import com.example.discussion.exceptions.UpdateException;
import com.example.discussion.mapper.CommentMapperOwn;
import com.example.discussion.model.entity.Comment;
import com.example.discussion.model.request.CommentRequestTo;
import com.example.discussion.model.response.CommentResponseTo;
import com.example.discussion.repository.CommentRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Service
@Validated
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapperOwn noteMapperDisc;

    public CommentResponseTo getNoteById(Long id) throws NotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException(404L, "Comment not found!"));
        return noteMapperDisc.entityToResponse(comment);
    }

    public List<CommentResponseTo> getNotes() {
        return toNoteResponseList(commentRepository.findAll());
    }

    public CommentResponseTo saveNote(@Valid CommentRequestTo comment, String country) {
        Comment commentToSave = noteRequestToNote(comment);
        commentToSave.setId(getId());
        commentToSave.setCountry(getCountry(country));
        return noteToNoteResponse(commentRepository.save(commentToSave));
    }

    public void deleteNote(Long id) throws DeleteException {
        Optional<Comment> note = commentRepository.findById(id).stream().findFirst();
        if (note.isEmpty()) {
            throw new NotFoundException(404L, "Comment not found!");
        } else {
            commentRepository.deleteByCountryAndIssueIdAndId(note.get().getCountry(), note.get().getIssueId(), note.get().getId());
        }
    }

    public CommentResponseTo updateNote(@Valid CommentRequestTo note, String country) throws UpdateException {
        Comment commentToUpdate = noteRequestToNote(note);
        commentToUpdate.setId(note.getId());
        commentToUpdate.setCountry(getCountry(country));
        Optional<Comment> commentOptional = commentRepository.findById(note.getId()).stream().findFirst();
        if (commentOptional.isEmpty()) {
            throw new UpdateException("Comment not found!", 404L);
        } else {
            commentRepository.deleteByCountryAndIssueIdAndId(commentOptional.get().getCountry(), commentOptional.get().getIssueId(), commentOptional.get().getId());
            return noteToNoteResponse(commentRepository.save(commentToUpdate));
        }
    }

    /*public List<NoteResponseTo> getNoteByIssueId(Long issueId) throws NotFoundException {
        List<Note> notes = noteRepository.findByStoryId(issueId);
        if (notes.isEmpty()) {
            throw new NotFoundException(404L, "Comment not found!");
        }
        return toNoteResponseList(notes);
    }*/

    private CommentResponseTo noteToNoteResponse(Comment comment) {
        CommentResponseTo commentResponseTo = new CommentResponseTo();
        commentResponseTo.setIssueId(comment.getIssueId());
        commentResponseTo.setId(comment.getId());
        commentResponseTo.setContent(comment.getContent());
        return commentResponseTo;
    }

    private List<CommentResponseTo> toNoteResponseList(List<Comment> comments) {
        List<CommentResponseTo> response = new ArrayList<>();
        for (Comment comment : comments) {
            response.add(noteToNoteResponse(comment));
        }
        return response;
    }

    private Comment noteRequestToNote(CommentRequestTo requestTo) {
        Comment comment = new Comment();
        comment.setId(requestTo.getId());
        comment.setContent(requestTo.getContent());
        comment.setIssueId(requestTo.getIssueId());
        return comment;
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
            loadMap.put(country, commentRepository.countByCountry(country)*(1-languageMap.get(country)));
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

    /*private NoteResponseTo findByCountryAndId(NoteRequestTo noteRequestTo) {
        Note entity = noteMapper.requestToEntity(noteRequestTo);
        return noteMapper.entityToResponse(noteRepository.findByCountryAndId(entity.getCountry(), entity.getId()).orElseThrow());
    }*/

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
