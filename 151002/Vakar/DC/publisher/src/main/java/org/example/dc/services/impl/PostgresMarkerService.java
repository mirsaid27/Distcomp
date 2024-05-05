package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Marker;
import org.example.dc.model.MarkerDto;
import org.example.dc.services.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresMarkerService implements MarkerService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<MarkerDto> getMarkers() {
        return jdbcTemplate.query("select * from tbl_marker", new BeanPropertyRowMapper<>(Marker.class)).stream()
                .map(marker -> converter.convert(marker)).collect(Collectors.toList());
    }

    @Override
    public MarkerDto getMarkerById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_marker where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Marker.class)).get(0));
    }

    @Override
    public boolean delete(int id) throws Exception {
        if (jdbcTemplate.query("select * from tbl_marker", new BeanPropertyRowMapper<>(Marker.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_marker where id=?", new Object[]{id});
        return true;
    }

    @Override
    public MarkerDto updateMarker(MarkerDto markerDto) {
        jdbcTemplate.update("update tbl_marker set name=? where id=?",
                new Object[]{markerDto.getName(), markerDto.getId()});
        return markerDto;
    }

    @Override
    public MarkerDto createMarker(MarkerDto markerDto) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_marker").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(markerDto));
        markerDto.setId(number.intValue());
        return markerDto;
    }
}
