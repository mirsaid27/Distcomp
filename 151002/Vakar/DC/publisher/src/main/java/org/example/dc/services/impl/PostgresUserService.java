package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.User;
import org.example.dc.model.UserDto;
import org.example.dc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresUserService implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<UserDto> getUsers() {
        return jdbcTemplate.query("select * from tbl_user", new BeanPropertyRowMapper<>(User.class)).stream()
                .map(editor -> converter.convert(editor)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_user where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).get(0));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = converter.convert(userDto);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_user").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(user));
        userDto.setId(number.intValue());
        return userDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        if (jdbcTemplate.query("select * from tbl_user where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_user where id=?", new Object[]{id});
        return true;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = converter.convert(userDto);
        jdbcTemplate.update("update tbl_user set login=?, password=?, firstname=?, lastname=? where id=?",
                new Object[]{user.getLogin(), user.getPassword(),
                        userDto.getFirstname(), userDto.getLastname(), userDto.getId()});
        return userDto;
    }
}