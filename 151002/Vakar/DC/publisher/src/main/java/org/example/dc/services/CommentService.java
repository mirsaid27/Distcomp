package org.example.dc.services;

import org.example.dc.model.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments();
    CommentDto getCommentById(int id);
    boolean delete(int id) throws Exception;
    CommentDto create(CommentDto commentDto);
    CommentDto update(CommentDto commentDto);
}
