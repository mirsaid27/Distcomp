package com.example.discussion.service;


import com.example.discussion.exceptions.DeleteException;
import com.example.discussion.exceptions.NotFoundException;
import com.example.discussion.exceptions.UpdateException;
import com.example.discussion.mapper.MessageMapperOwn;
import com.example.discussion.model.entity.Message;
import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;
import com.example.discussion.repository.MessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapperOwn noteMapperDisc;

    public MessageResponseTo getNoteById(Long id) throws NotFoundException {
        Message comment = messageRepository.findById(id).orElseThrow(
                () -> new NotFoundException(404L, "Comment not found!"));
        return noteMapperDisc.entityToResponse(comment);
    }

    public List<MessageResponseTo> getNotes() {
        return toNoteResponseList(messageRepository.findAll());
    }

    public MessageResponseTo saveNote(@Valid MessageRequestTo comment, String country) {
        Message messageToSave = noteRequestToNote(comment);
        messageToSave.setId(getId());
        messageToSave.setCountry(getCountry(country));
        return noteToNoteResponse(messageRepository.save(messageToSave));
    }

    public void deleteNote(Long id) throws DeleteException {
        Optional<Message> note = messageRepository.findById(id).stream().findFirst();
        if (note.isEmpty()) {
            throw new DeleteException("Comment not found!", 404L);
        } else {
            messageRepository.deleteByCountryAndTweetIdAndId(note.get().getCountry(), note.get().getTweetId(), note.get().getId());
        }
    }

    public MessageResponseTo updateNote(@Valid MessageRequestTo note, String country) throws UpdateException {
        Message messageToUpdate = noteRequestToNote(note);
        messageToUpdate.setId(note.getId());
        messageToUpdate.setCountry(getCountry(country));
        Optional<Message> commentOptional = messageRepository.findById(note.getId()).stream().findFirst();
        if (commentOptional.isEmpty()) {
            throw new UpdateException("Comment not found!", 404L);
        } else {
            messageRepository.deleteByCountryAndTweetIdAndId(commentOptional.get().getCountry(), commentOptional.get().getTweetId(), commentOptional.get().getId());
            return noteToNoteResponse(messageRepository.save(messageToUpdate));
        }
    }

    /*public List<NoteResponseTo> getNoteByIssueId(Long issueId) throws NotFoundException {
        List<Note> notes = noteRepository.findByStoryId(issueId);
        if (notes.isEmpty()) {
            throw new NotFoundException(404L, "Comment not found!");
        }
        return toNoteResponseList(notes);
    }*/

    private MessageResponseTo noteToNoteResponse(Message message) {
        MessageResponseTo messageResponseTo = new MessageResponseTo();
        messageResponseTo.setTweetId(message.getTweetId());
        messageResponseTo.setId(message.getId());
        messageResponseTo.setContent(message.getContent());
        return messageResponseTo;
    }

    private List<MessageResponseTo> toNoteResponseList(List<Message> messages) {
        List<MessageResponseTo> response = new ArrayList<>();
        for (Message message : messages) {
            response.add(noteToNoteResponse(message));
        }
        return response;
    }

    private Message noteRequestToNote(MessageRequestTo requestTo) {
        Message message = new Message();
        message.setId(requestTo.getId());
        message.setContent(requestTo.getContent());
        message.setTweetId(requestTo.getTweetId());
        return message;
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
            loadMap.put(country, messageRepository.countByCountry(country)*(1-languageMap.get(country)));
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
