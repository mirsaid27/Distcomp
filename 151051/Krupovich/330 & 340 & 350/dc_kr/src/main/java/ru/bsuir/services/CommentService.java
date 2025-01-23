package ru.bsuir.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.CommentRequestTo;
import ru.bsuir.dto.response.CommentResponseTo;
import ru.bsuir.entity.Comment;
import ru.bsuir.entity.Tweet;
import ru.bsuir.irepositories.ICommentRepository;
import ru.bsuir.irepositories.ITweetRepository;
import ru.bsuir.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class CommentService {

    private final ICommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final ITweetRepository tweetRepository;

    @CacheEvict(value = {"comments", "commentsList"}, allEntries = true)
    public CommentResponseTo createComment(CommentRequestTo commentRequest) {

        Tweet tweet = tweetRepository.findById(commentRequest.getTweetId())
                .orElseThrow(() -> new DataIntegrityViolationException("Tweet not found"));

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setTweet(tweet);
        comment = commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Cacheable(value = "comments", key = "#id")
    public CommentResponseTo getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(commentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Cacheable(value = "commentsList")
    public List<CommentResponseTo> getAllComment(){
        return commentRepository.findAll().stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = {"comments", "commentsList"}, key = "#id", allEntries = true)
    public CommentResponseTo updateComment(Long id, CommentRequestTo commentRequest) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
        return commentMapper.toDTO((comment));
    }

    @CacheEvict(value = {"comments", "commentsList"}, key = "#id", allEntries = true)
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");

        }
        commentRepository.deleteById(id);
    }


}
