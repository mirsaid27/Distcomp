package com.example.discussion.service;

import com.example.discussion.model.dto.CommentRequestTo;
import com.example.discussion.model.dto.CommentResponseTo;
import com.example.discussion.mapper.CommentMapper;
import com.example.discussion.model.Comment;
import com.example.discussion.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentResponseTo createComment(CommentRequestTo commentRequestDto) {
        Comment comment = commentMapper.toEntity(commentRequestDto);

        try {
            comment = commentRepository.save(comment);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Comment already exists or invalid data");
        }
        return commentMapper.toResponse(comment);
    }

public CommentResponseTo getCommentById(Long id) {
    Optional<Comment> commentOptional = commentRepository.findById(id);
    return commentOptional
            .map(commentMapper::toResponse)
            .orElseGet(() -> createNotFoundResponse(id));
}
    private CommentResponseTo createNotFoundResponse(Long id) {
        CommentResponseTo response = new CommentResponseTo();
        response.setErrorExist(true);
        return response;
    }

    public List<CommentResponseTo> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    public CommentResponseTo updateComment(Long id, CommentRequestTo commentRequestDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        existingComment.setContent(commentRequestDto.getContent());

        commentRepository.save(existingComment);
        return commentMapper.toResponse(existingComment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.deleteById(id);
    }
}
