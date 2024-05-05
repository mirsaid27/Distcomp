package by.bsuir.publisher.service;

import by.bsuir.publisher.model.request.NoteRequestTo;
import by.bsuir.publisher.model.response.NoteResponseTo;
import by.bsuir.publisher.event.*;
import by.bsuir.publisher.service.exceptions.NoteExchangeFailedException;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.model.entity.NoteState;
import by.bsuir.publisher.dao.NewsRepository;
import by.bsuir.publisher.service.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteMapper noteMapper;
    private final KafkaNoteClient KafkaNoteClient;
    private static final SecureRandom random;
    private final NewsRepository NewsRepository;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }

    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    public List<NoteResponseTo> getAll() {
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_ALL);
        OutTopicMessage outTopicMsg = KafkaNoteClient.sync(inTopicMsg);
        return noteMapper.toDto(outTopicMsg.resultList());
    }

    public void deleteById(Long id) {
        NoteInTopicTo inTopicDto = new NoteInTopicTo();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.DELETE_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = KafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicTo deleted = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("note with id = " + id + " is not found", 40489));
        if (!Objects.equals(id, deleted.id())) {
            throw new NoteExchangeFailedException("Note request return invalid id: expected = " + id + ", returned = " + deleted.id());
        }
    }

    public NoteResponseTo create(NoteRequestTo dto, Locale locale) {
        Long newsId = dto.newsId();
        if (!NewsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("news with id = " + newsId + " doesn't exist", 40490);
        }
        NoteInTopicTo inTopicDto = noteMapper.toInTopicDto(dto);
        inTopicDto.setId(getTimeBasedId());
        inTopicDto.setCountry(locale);
        inTopicDto.setState(NoteState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.CREATE, inTopicDto);
        System.err.println("\n\n" + inTopicMsg.operation() + "  " + inTopicMsg.noteDto().getContent()
                + "  " + inTopicMsg.noteDto().getState() + "  " + inTopicMsg.noteDto().getCountry() + "  " +
                inTopicMsg.noteDto().getId()  + "\n\n");
        OutTopicMessage outTopicMsg = KafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicTo created = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoteExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return noteMapper.toDto(created);
    }

    public NoteResponseTo getById(Long id) {
        NoteInTopicTo inTopicDto = new NoteInTopicTo();
        inTopicDto.setId(id);
        InTopicMessage inTopicMsg = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, inTopicDto);
        OutTopicMessage outTopicMsg = KafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicTo noteOut = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found", 40411));
        return noteMapper.toDto(noteOut);
    }

    public NoteResponseTo update(NoteRequestTo dto) {
        Long newsId = dto.newsId();
        if (!NewsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("news with id = " + newsId + " doesn't exist", 40424);
        }
        NoteInTopicTo inTopicDto = noteMapper.toInTopicDto(dto);
        inTopicDto.setState(NoteState.PENDING);
        InTopicMessage inTopicMsg = new InTopicMessage(
                InTopicMessage.Operation.UPDATE, inTopicDto);
        OutTopicMessage outTopicMsg = KafkaNoteClient.sync(inTopicMsg);
        NoteOutTopicTo updated = outTopicMsg.resultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoteExchangeFailedException("Getting note with id = "
                        + inTopicDto.getId() + " failed"));
        return noteMapper.toDto(updated);
    }
}