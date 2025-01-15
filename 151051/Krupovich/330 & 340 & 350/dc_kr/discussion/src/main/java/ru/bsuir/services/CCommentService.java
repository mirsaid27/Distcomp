package ru.bsuir.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.CCommentRequestTo;
import ru.bsuir.dto.response.CCommentResponseTo;
import ru.bsuir.entity.CComment;
import ru.bsuir.irepositories.CCommentRepository;
import ru.bsuir.mapper.CCommentMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CCommentService {

    private final CCommentRepository commentRepository;
    private final CCommentMapper commentMapper;

    public CCommentResponseTo createComment(CCommentRequestTo commentRequest) {
        CComment comment = commentMapper.toEntity(commentRequest);

        try {
            comment = commentRepository.save(comment);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Comment already exists or invalid data");
        }
        return commentMapper.toDTO(comment);
    }

    public CCommentResponseTo getCommentById(Long id) {
        Optional<CComment> commentOpt = commentRepository.findById(id);
        return commentOpt
                .map(commentMapper::toDTO)
                .orElseGet(() -> createNotFoundResponse(id));
    }

    private CCommentResponseTo createNotFoundResponse(Long id) {
        CCommentResponseTo response = new CCommentResponseTo();
        response.setErrorExist(true);
        return response;
    }

    public List<CCommentResponseTo> getAllComment(){
        return commentRepository.findAll().stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    public CCommentResponseTo updateComment(Long id, CCommentRequestTo commentRequest) {
        CComment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        existingComment.setContent(commentRequest.getContent());

        commentRepository.save(existingComment);
        return commentMapper.toDTO(existingComment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");

        }
        commentRepository.deleteById(id);
    }


}

