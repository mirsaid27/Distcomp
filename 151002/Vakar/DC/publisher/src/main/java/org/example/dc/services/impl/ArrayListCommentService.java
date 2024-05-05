package org.example.dc.services.impl;

import org.example.dc.model.CommentDto;
import org.example.dc.services.CommentService;

import java.util.ArrayList;
import java.util.List;

public class ArrayListCommentService implements CommentService {
    private static int id = 1;
    private List<CommentDto> comments = new ArrayList<>();

    @Override
    public List<CommentDto> getComments() {
        return comments;
    }

    @Override
    public CommentDto getCommentById(int id) {
        return comments.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean delete(int id) throws Exception {
        CommentDto commentDto = comments.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        comments.remove(commentDto);
        return true;
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        commentDto.setId(id++);
        comments.add(commentDto);
        return commentDto;
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        CommentDto comment = comments.stream()
                .filter(n -> n.getId() == commentDto.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        comment.setContent(commentDto.getContent());
        comment.setStoryId(comment.getStoryId());
        return comment;
    }
}
