package org.example.dc.model;

import org.springframework.stereotype.Service;

@Service
public class Converter {

    public CommentDto convert(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setStoryId(comment.getStory_id());
        return commentDto;
    }

    public Comment convert(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setStory_id(commentDto.getStoryId());
        return comment;
    }
}