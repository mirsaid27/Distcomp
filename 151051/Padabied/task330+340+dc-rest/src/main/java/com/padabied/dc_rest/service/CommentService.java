package com.padabied.dc_rest.service;

import com.padabied.dc_rest.mapper.CommentMapper;
import com.padabied.dc_rest.model.Comment;
import com.padabied.dc_rest.model.Story;
import com.padabied.dc_rest.model.dto.requests.CommentRequestTo;
import com.padabied.dc_rest.model.dto.responses.CommentResponseTo;
import com.padabied.dc_rest.repository.interfaces.CommentRepository;
import com.padabied.dc_rest.repository.interfaces.StoryRepository;
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
    private final StoryRepository storyRepository;
    @CacheEvict(value = {"comments", "commentsList"}, allEntries = true)
     public CommentResponseTo createComment(CommentRequestTo commentRequestDto) {
         // Найти Story по переданному storyId
         Story story = storyRepository.findById(commentRequestDto.getStoryId())
                 .orElseThrow(() -> new DataIntegrityViolationException("Story not found"));

         Comment comment = commentMapper.toEntity(commentRequestDto);
         comment.setStory(story);
        System.out.println("Story ID перед сохранением: " + story.getId());

         comment = commentRepository.save(comment);
        System.out.println("Story ID после сохранения: " + comment.getStory().getId());

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
