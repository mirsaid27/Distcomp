package com.example.discussion.mapper;

import com.example.discussion.model.entity.Comment;
import com.example.discussion.model.response.CommentResponseTo;
import org.springframework.stereotype.Service;

@Service
public class CommentMapperOwn {
    public CommentResponseTo entityToResponse(Comment entity) {
        return CommentResponseTo.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .issueId(entity.getIssueId())
                .build();
    }
}
