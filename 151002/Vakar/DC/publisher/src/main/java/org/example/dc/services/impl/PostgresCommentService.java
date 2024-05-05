package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Comment;
import org.example.dc.model.CommentDto;
import org.example.dc.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresCommentService implements CommentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<CommentDto> getComments() {
        return jdbcTemplate.query("select * from tbl_comment", new BeanPropertyRowMapper<>(Comment.class)).stream()
                .map(note -> converter.convert(note)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_comment where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Comment.class)).get(0));
    }

    @Override
    public boolean delete(int id) throws Exception {
        if(jdbcTemplate.query("select * from tbl_comment", new BeanPropertyRowMapper<>(Comment.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_comment where id=?", new Object[]{id});
        return true;
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        Comment comment = converter.convert(commentDto);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_comment").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(comment));
        commentDto.setId(number.intValue());
        return commentDto;
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        Comment comment = converter.convert(commentDto);
        jdbcTemplate.update("update tbl_comment set story_id=?, content=? where id=?",
                new Object[]{comment.getStory_id(), comment.getContent(), comment.getId()});
        return commentDto;
    }
}