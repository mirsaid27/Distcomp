package ru.bsuir.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.CommentRequestTo;
import ru.bsuir.dto.response.CommentResponseTo;
import ru.bsuir.entity.Comment;
import ru.bsuir.irepositories.ICommentRepository;
import ru.bsuir.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service

public class CommentService {

    private final ICommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(ICommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponseTo createComment(CommentRequestTo commentRequest) {
        Comment comment = commentMapper.toEntity(commentRequest);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    public Optional<CommentResponseTo> getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(commentMapper::toDTO);
    }

    public List<CommentResponseTo> getAllComment(){
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false)
                .map(commentMapper::toDTO)
                .toList();
    }

    public Optional<CommentResponseTo> updateComment(Long id, CommentRequestTo commentRequest) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            Comment updatedComment = commentMapper.toEntity(commentRequest);
            updatedComment.setId(id);
            commentRepository.save(updatedComment);
            return Optional.of(commentMapper.toDTO(updatedComment));
        }
        throw new EntityNotFoundException("Comment not found with id: " + id);
    }

    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
