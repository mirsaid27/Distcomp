package com.rmakovetskij.dc_rest.service;

import com.rmakovetskij.dc_rest.mapper.MessageMapper;
import com.rmakovetskij.dc_rest.model.Message;
import com.rmakovetskij.dc_rest.model.Issue;
import com.rmakovetskij.dc_rest.model.dto.requests.MessageRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.MessageResponseTo;
import com.rmakovetskij.dc_rest.repository.interfaces.MessageRepository;
import com.rmakovetskij.dc_rest.repository.interfaces.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final IssueRepository issueRepository;
    public MessageResponseTo createMessage(MessageRequestTo messageRequestDto) {
        // Найти Issue по переданному issueId
        Issue issue = issueRepository.findById(messageRequestDto.getIssueId())
                .orElseThrow(() -> new DataIntegrityViolationException("Issue not found")); // Если история не найдена, выбрасываем исключение

        // Преобразовать DTO в сущность Message
        Message message = messageMapper.toEntity(messageRequestDto);
        message.setIssue(issue); // Установить связь с найденной Issue

        // Сохранить комментарий
        message = messageRepository.save(message);

        // Вернуть ответ
        return messageMapper.toResponse(message);
    }

    // Получить комментарий по id
    public MessageResponseTo getMessageById(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        return messageOptional.map(messageMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    // Получить все комментарии
    public List<MessageResponseTo> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    // Обновить комментарий по id
    public MessageResponseTo updateMessage(Long id, MessageRequestTo messageRequestDto) {
        Message existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        existingMessage.setContent(messageRequestDto.getContent());

        messageRepository.save(existingMessage);
        return messageMapper.toResponse(existingMessage);
    }

    // Удалить комментарий по id
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        messageRepository.deleteById(id);
    }

}
