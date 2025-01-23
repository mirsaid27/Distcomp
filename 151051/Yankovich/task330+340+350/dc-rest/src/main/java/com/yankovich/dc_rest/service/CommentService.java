package com.yankovich.dc_rest.service;

import com.yankovich.dc_rest.mapper.CommentMapper;
import com.yankovich.dc_rest.model.Comment;
import com.yankovich.dc_rest.model.Tweet;
import com.yankovich.dc_rest.model.dto.requests.CommentRequestTo;
import com.yankovich.dc_rest.model.dto.responses.CommentResponseTo;
import com.yankovich.dc_rest.repository.interfaces.CommentRepository;
import com.yankovich.dc_rest.repository.interfaces.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TweetRepository tweetRepository;
    @CacheEvict(value = {"comments", "commentsList"}, allEntries = true)
     public CommentResponseTo createComment(CommentRequestTo commentRequestDto) {
         Tweet tweet = tweetRepository.findById(commentRequestDto.getTweetId())
                 .orElseThrow(() -> new DataIntegrityViolationException("Tweet not found"));

         Comment comment = commentMapper.toEntity(commentRequestDto);
         comment.setTweet(tweet);
        System.out.println("Tweet ID перед сохранением: " + tweet.getId());

         comment = commentRepository.save(comment);
        System.out.println("Tweet ID после сохранения: " + comment.getTweet().getId());

         return commentMapper.toResponse(comment);
     }
    @Cacheable(value = "comments", key = "#id")
    public CommentResponseTo getCommentById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        return commentOptional.map(commentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    @Cacheable(value = "commentsList")
    public List<CommentResponseTo> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toResponse)
                .toList();
    }
    @CacheEvict(value = {"comments", "commentsList"}, key = "#id", allEntries = true)
    public CommentResponseTo updateComment(Long id, CommentRequestTo commentRequestDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        existingComment.setContent(commentRequestDto.getContent());

        commentRepository.save(existingComment);
        return commentMapper.toResponse(existingComment);
    }
    @CacheEvict(value = {"comments", "commentsList"}, key = "#id", allEntries = true)
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.deleteById(id);
    }
}
