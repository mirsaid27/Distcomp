package com.example.rvlab1.integration;

import com.example.rvlab1.RvLab1Application;
import com.example.rvlab1.model.dto.request.IssueRequestTo;
import com.example.rvlab1.model.dto.request.IssueRequestWithIdTo;
import com.example.rvlab1.model.dto.request.UserRequestTo;
import com.example.rvlab1.model.dto.request.UserRequestWithIdTo;
import com.example.rvlab1.model.dto.response.IssueResponseTo;
import com.example.rvlab1.model.dto.response.UserResponseTo;
import com.example.rvlab1.model.service.Issue;
import com.example.rvlab1.model.service.User;
import com.example.rvlab1.storage.IssueStorage;
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
public class IssueApiTests {
    private static final String URL_PREFIX = "/api/v1.0";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IssueStorage issueStorage;

    @Test
    @DisplayName("Get issue by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_issues_posts.sql"})
    @SneakyThrows
    void getIssueByIdOk() {
        Long issueId = 10L;

        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/issues/{issueId}")
                        .buildAndExpand(issueId)
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        IssueResponseTo issueResponseTo = objectMapper.readValue(response.getContentAsString(), IssueResponseTo.class);
        Issue issue = issueStorage.findById(issueId).get();

        assertAll(
                () -> assertEquals(issue.getId(), issueResponseTo.getId()),
                () -> assertEquals(issue.getUserId(), issueResponseTo.getUserId()),
                () -> assertEquals(issue.getTitle(), issueResponseTo.getTitle()),
                () -> assertEquals(issue.getContent(), issueResponseTo.getContent()),
                () -> assertEquals(issue.getCreated(), issueResponseTo.getCreated()),
                () -> assertEquals(issue.getModified(), issueResponseTo.getModified())
        );
    }

    @Test
    @DisplayName("Get all issues - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_issues_posts.sql"})
    @SneakyThrows
    void getAllIssuesOk() {
        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/issues")
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<IssueResponseTo> issueResponseToList = objectMapper.readValue(response.getContentAsString(), new TypeReference<>(){});
        List<Issue> issueList = issueStorage.findAll();

        assertAll(
                () -> assertEquals(issueList.get(0).getId(), issueResponseToList.get(0).getId()),
                () -> assertEquals(issueList.get(0).getUserId(), issueResponseToList.get(0).getUserId()),
                () -> assertEquals(issueList.get(0).getTitle(), issueResponseToList.get(0).getTitle()),
                () -> assertEquals(issueList.get(0).getContent(), issueResponseToList.get(0).getContent()),
                () -> assertEquals(issueList.get(0).getCreated(), issueResponseToList.get(0).getCreated()),
                () -> assertEquals(issueList.get(0).getModified(), issueResponseToList.get(0).getModified()),

                () -> assertEquals(issueList.get(1).getId(), issueResponseToList.get(1).getId()),
                () -> assertEquals(issueList.get(1).getUserId(), issueResponseToList.get(1).getUserId()),
                () -> assertEquals(issueList.get(1).getTitle(), issueResponseToList.get(1).getTitle()),
                () -> assertEquals(issueList.get(1).getContent(), issueResponseToList.get(1).getContent()),
                () -> assertEquals(issueList.get(1).getCreated(), issueResponseToList.get(1).getCreated()),
                () -> assertEquals(issueList.get(1).getModified(), issueResponseToList.get(1).getModified())
        );
    }

    @Test
    @DisplayName("Create issue - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_users.sql"})
    @SneakyThrows
    void createIssueOk() {
        IssueRequestTo issueRequestTo = new IssueRequestTo()
                .setTitle("Title")
                .setContent("Content")
                .setUserId(10L);

        MockHttpServletResponse response = mvc.perform(post(UriComponentsBuilder.fromUriString(URL_PREFIX+"/issues")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(issueRequestTo))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        IssueResponseTo issueResponseTo = objectMapper.readValue(response.getContentAsString(), IssueResponseTo.class);
        List<Issue> issueList = issueStorage.findAll();
        Issue issue = issueList.get(0);

        assertAll(
                () -> assertEquals(1, issueList.size()),
                () -> assertEquals(issueRequestTo.getUserId(), issue.getUserId()),
                () -> assertEquals(issueRequestTo.getTitle(), issue.getTitle()),
                () -> assertEquals(issueRequestTo.getContent(), issue.getContent()),

                () -> assertEquals(issueResponseTo.getId(), issue.getId()),
                () -> assertEquals(issueResponseTo.getUserId(), issue.getUserId()),
                () -> assertEquals(issueResponseTo.getTitle(), issue.getTitle()),
                () -> assertEquals(issueResponseTo.getContent(), issue.getContent())
        );
    }

    @Test
    @DisplayName("Delete issue by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_issues_posts.sql"})
    @SneakyThrows
    void deleteIssueByIdOk() {
        Long issueId = 10L;

        MockHttpServletResponse response = mvc.perform(delete(UriComponentsBuilder.fromUriString(URL_PREFIX+"/issues/{issueId}")
                        .buildAndExpand(issueId)
                        .toUriString()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals("", response.getContentAsString()),
                () -> assertTrue(issueStorage.findById(issueId).isEmpty())
        );
    }

    @Test
    @DisplayName("Update issue by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_issues_posts.sql"})
    @SneakyThrows
    void updateIssueByIdOk() {
        Long issueId = 10L, userId = 20L;
        IssueRequestWithIdTo issueRequestWithIdTo = (IssueRequestWithIdTo) new IssueRequestWithIdTo()
                .setId(issueId)
                .setTitle("New title")
                .setContent("New content")
                .setUserId(userId);

        MockHttpServletResponse response = mvc.perform(put(UriComponentsBuilder.fromUriString(URL_PREFIX+"/issues")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(issueRequestWithIdTo))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        IssueResponseTo issueResponseTo = objectMapper.readValue(response.getContentAsString(), IssueResponseTo.class);
        List<Issue> issueList = issueStorage.findAll();
        Issue issue = issueStorage.findById(issueId).get();

        assertAll(
                () -> assertEquals(2, issueList.size()),
                () -> assertEquals(issueRequestWithIdTo.getId(), issue.getId()),
                () -> assertEquals(issueRequestWithIdTo.getUserId(), issue.getUserId()),
                () -> assertEquals(issueRequestWithIdTo.getTitle(), issue.getTitle()),
                () -> assertEquals(issueRequestWithIdTo.getContent(), issue.getContent()),

                () -> assertEquals(issue.getId(), issueResponseTo.getId()),
                () -> assertEquals(issue.getUserId(), issueResponseTo.getUserId()),
                () -> assertEquals(issue.getTitle(), issueResponseTo.getTitle()),
                () -> assertEquals(issue.getContent(), issueResponseTo.getContent()),
                () -> assertEquals(issue.getCreated().toInstant(), issueResponseTo.getCreated().toInstant()),
                () -> assertEquals(issue.getModified().toInstant(), issueResponseTo.getModified().toInstant())
        );
    }
}
