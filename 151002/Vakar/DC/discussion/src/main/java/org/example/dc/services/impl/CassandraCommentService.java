package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Comment;
import org.example.dc.model.CommentDto;
import org.example.dc.model.CommentRepository;
import org.example.dc.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

public class CassandraCommentService implements CommentService {

    private CommentRepository commentRepository;

    private Converter converter;

    @Override
    public List<CommentDto> getComments() {
        return commentRepository.findAll().stream()
                .map(note -> converter.convert(note))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(int id) {
        return converter.convert(commentRepository.findById(id).get());
    }

    @Override
    public boolean delete(int id) throws Exception {
        getCommentById(id);
        commentRepository.deleteById(id);
        return true;
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        Comment comment = converter.convert(commentDto);
        int id = commentRepository.save(comment).getId();
        commentDto.setId(id);
        return commentDto;
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        Comment comment = converter.convert(commentDto);
        commentRepository.deleteById(commentDto.getId());
        commentRepository.save(comment);
        return commentDto;
    }
}
