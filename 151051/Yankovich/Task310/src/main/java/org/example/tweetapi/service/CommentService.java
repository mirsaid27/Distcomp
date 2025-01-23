package org.example.tweetapi.service;

import lombok.AllArgsConstructor;
import org.example.tweetapi.mapper.CommentMapper;
import org.example.tweetapi.model.dto.request.CommentRequestTo;
import org.example.tweetapi.model.dto.response.CommentResponseTo;
import org.example.tweetapi.model.entity.Comment;
import org.example.tweetapi.model.entity.Tweet;
import org.example.tweetapi.repository.CommentRepository;
import org.example.tweetapi.repository.TweetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TweetRepository tweetRepository;
    public CommentResponseTo createComment(CommentRequestTo commentRequestDto) {
        if (!tweetRepository.existsById(commentRequestDto.getTweetId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tweet not found");
        }
        Tweet tweet = tweetRepository.findById(commentRequestDto.getTweetId())
                .orElseThrow(() -> new DataIntegrityViolationException("Tweet not found")); // Если история не найдена, выбрасываем исключение

        // Преобразовать DTO в сущность Comment
        Comment comment = commentMapper.toEntity(commentRequestDto);
        comment.setTweet(tweet); // Установить связь с найденной Tweet

        // Сохранить комментарий
        comment = commentRepository.save(comment);

        // Вернуть ответ
        return commentMapper.toResponse(comment);
    }

    // Получить комментарий по id
    public CommentResponseTo getCommentById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        return commentOptional.map(commentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    // Получить все комментарии
    public List<CommentResponseTo> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    // Обновить комментарий по id
    public CommentResponseTo updateComment(Long id, CommentRequestTo commentRequestDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        existingComment.setContent(commentRequestDto.getContent());

        commentRepository.save(existingComment);
        return commentMapper.toResponse(existingComment);
    }

    // Удалить комментарий по id
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.deleteById(id);
    }

}