package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Story;
import org.example.dc.model.StoryDto;
import org.example.dc.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresStoryService implements StoryService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<StoryDto> getStorys() {
        return jdbcTemplate.query("select * from tbl_story", new BeanPropertyRowMapper<>(Story.class)).stream()
                .map(story -> converter.convert(story)).collect(Collectors.toList());
    }

    @Override
    public StoryDto getStoryById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_story where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Story.class)).get(0));
    }

    @Override
    public StoryDto createStory(StoryDto storyDto) {
        storyDto.setCreated(new Date(System.currentTimeMillis()));
        storyDto.setModified(new Date(System.currentTimeMillis()));
        Story story = converter.convert(storyDto);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_story").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(story));
        storyDto.setId(number.intValue());
        return storyDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        if(jdbcTemplate.query("select * from tbl_story where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Story.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_story where id=?", new Object[]{id});
        return true;
    }

    @Override
    public StoryDto update(StoryDto storyDto) {
        storyDto.setModified(new Date(System.currentTimeMillis()));
        Story story = converter.convert(storyDto);
        jdbcTemplate.update("update tbl_story set user_id=?, title=?, content=?," +
                "created=?, modified=? where id=?",
                new Object[]{story.getUser_id(), story.getTitle(), story.getContent(), story.getCreated(),
                        story.getModified(), story.getId()});
        return storyDto;
    }
}
