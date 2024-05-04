package com.example.rvlab1.integration;

import com.example.rvlab1.RvLab1Application;
import com.example.rvlab1.model.dto.request.UserRequestTo;
import com.example.rvlab1.model.dto.request.UserRequestWithIdTo;
import com.example.rvlab1.model.dto.response.UserResponseTo;
import com.example.rvlab1.model.service.User;
import com.example.rvlab1.storage.UserStorage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RvLab1Application.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserApiTests {
    private static final String URL_PREFIX = "/api/v1.0";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserStorage userStorage;

    @Test
    @DisplayName("Get user by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_users.sql"})
    @SneakyThrows
    void getUserByIdOk() {
        Long userId = 10L;

        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/users/{userId}")
                .buildAndExpand(userId)
                .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        UserResponseTo userResponseTo = objectMapper.readValue(response.getContentAsString(), UserResponseTo.class);
        User user = userStorage.findById(userId).get();

        assertAll(
                () -> assertEquals(user.getLogin(), userResponseTo.getLogin()),
                () -> assertEquals(user.getFirstname(), userResponseTo.getFirstname()),
                () -> assertEquals(user.getLastname(), userResponseTo.getLastname()),
                () -> assertEquals(user.getPassword(), userResponseTo.getPassword())
        );
    }

    @Test
    @DisplayName("Get all users - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_users.sql"})
    @SneakyThrows
    void getAllUsersOk() {
        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/users")
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<UserResponseTo> userResponseToList = objectMapper.readValue(response.getContentAsString(), new TypeReference<>(){});
        List<User> userList = userStorage.findAll();

        assertAll(
                () -> assertEquals(userList.get(0).getId(), userResponseToList.get(0).getId()),
                () -> assertEquals(userList.get(0).getLogin(), userResponseToList.get(0).getLogin()),
                () -> assertEquals(userList.get(0).getFirstname(), userResponseToList.get(0).getFirstname()),
                () -> assertEquals(userList.get(0).getLastname(), userResponseToList.get(0).getLastname()),
                () -> assertEquals(userList.get(0).getPassword(), userResponseToList.get(0).getPassword()),

                () -> assertEquals(userList.get(1).getId(), userResponseToList.get(1).getId()),
                () -> assertEquals(userList.get(1).getLogin(), userResponseToList.get(1).getLogin()),
                () -> assertEquals(userList.get(1).getFirstname(), userResponseToList.get(1).getFirstname()),
                () -> assertEquals(userList.get(1).getLastname(), userResponseToList.get(1).getLastname()),
                () -> assertEquals(userList.get(1).getPassword(), userResponseToList.get(1).getPassword())
        );
    }

    @Test
    @DisplayName("Create user - Ok")
    @Sql({"/sql/clean.sql"})
    @SneakyThrows
    void createUserOk() {
        UserRequestTo userRequestTo = new UserRequestTo()
                .setLogin("login")
                .setPassword("password")
                .setFirstname("firstname")
                .setLastname("lastname");

        MockHttpServletResponse response = mvc.perform(post(UriComponentsBuilder.fromUriString(URL_PREFIX+"/users")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequestTo))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        UserResponseTo userResponseTo = objectMapper.readValue(response.getContentAsString(), UserResponseTo.class);
        List<User> userList = userStorage.findAll();
        User user = userList.get(0);

        assertAll(
                () -> assertEquals(1, userList.size()),
                () -> assertEquals(userRequestTo.getLogin(), user.getLogin()),
                () -> assertEquals(userRequestTo.getPassword(), user.getPassword()),
                () -> assertEquals(userRequestTo.getFirstname(), user.getFirstname()),
                () -> assertEquals(userRequestTo.getLastname(), user.getLastname()),

                () -> assertEquals(user.getId(), userResponseTo.getId()),
                () -> assertEquals(user.getLogin(), userResponseTo.getLogin()),
                () -> assertEquals(user.getPassword(), userResponseTo.getPassword()),
                () -> assertEquals(user.getFirstname(), userResponseTo.getFirstname()),
                () -> assertEquals(user.getLastname(), userResponseTo.getLastname())
        );
    }

    @Test
    @DisplayName("Delete user by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_users.sql"})
    @SneakyThrows
    void deleteUserByIdOk() {
        Long userId = 10L;

        MockHttpServletResponse response = mvc.perform(delete(UriComponentsBuilder.fromUriString(URL_PREFIX+"/users/{userId}")
                        .buildAndExpand(userId)
                        .toUriString()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals("", response.getContentAsString()),
                () -> assertTrue(userStorage.findById(userId).isEmpty())
        );
    }

    @Test
    @DisplayName("Update user by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_users.sql"})
    @SneakyThrows
    void updateUserByIdOk() {
        Long userId = 10L;
        UserRequestWithIdTo userRequestWithIdTo = (UserRequestWithIdTo) new UserRequestWithIdTo()
                .setId(userId)
                .setFirstname("New firstname")
                .setLastname("New lastname")
                .setLogin("New login")
                .setPassword("New password");

        MockHttpServletResponse response = mvc.perform(put(UriComponentsBuilder.fromUriString(URL_PREFIX+"/users")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequestWithIdTo))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        UserResponseTo userResponseTo = objectMapper.readValue(response.getContentAsString(), UserResponseTo.class);
        List<User> userList = userStorage.findAll();
        User user = userStorage.findById(userId).get();

        assertAll(
                () -> assertEquals(2, userList.size()),
                () -> assertEquals(userRequestWithIdTo.getId(), user.getId()),
                () -> assertEquals(userRequestWithIdTo.getLogin(), user.getLogin()),
                () -> assertEquals(userRequestWithIdTo.getPassword(), user.getPassword()),
                () -> assertEquals(userRequestWithIdTo.getFirstname(), user.getFirstname()),
                () -> assertEquals(userRequestWithIdTo.getLastname(), user.getLastname()),

                () -> assertEquals(user.getId(), userResponseTo.getId()),
                () -> assertEquals(user.getLogin(), userResponseTo.getLogin()),
                () -> assertEquals(user.getPassword(), userResponseTo.getPassword()),
                () -> assertEquals(user.getFirstname(), userResponseTo.getFirstname()),
                () -> assertEquals(user.getLastname(), userResponseTo.getLastname())
        );
    }
}
