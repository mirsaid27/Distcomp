package com.example.rvlab1.integration;

import com.example.rvlab1.RvLab1Application;
import com.example.rvlab1.model.dto.request.LabelRequestTo;
import com.example.rvlab1.model.dto.request.LabelRequestWithIdTo;
import com.example.rvlab1.model.dto.response.LabelResponseTo;
import com.example.rvlab1.model.service.Label;
import com.example.rvlab1.storage.LabelStorage;
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
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RvLab1Application.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class LabelApiTests {
    private static final String URL_PREFIX = "/api/v1.0";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LabelStorage labelStorage;

    @Test
    @DisplayName("Get label by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_labels.sql"})
    @SneakyThrows
    void getLabelByIdOk() {
        Long labelId = 10L;

        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/labels/{labelId}")
                        .buildAndExpand(labelId)
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        LabelResponseTo labelResponseTo = objectMapper.readValue(response.getContentAsString(), LabelResponseTo.class);
        Label label = labelStorage.findById(labelId).get();

        assertAll(
                () -> assertEquals(label.getId(), labelResponseTo.getId()),
                () -> assertEquals(label.getName(), labelResponseTo.getName()),
                () -> assertEquals(label.getIssues().stream().findFirst().get().getId(), labelResponseTo.getIssueId())
        );
    }

    @Test
    @DisplayName("Get all labels - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_labels.sql"})
    @SneakyThrows
    void getAllLabelsOk() {
        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/labels")
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<LabelResponseTo> labelResponseToList = objectMapper.readValue(response.getContentAsString(), new TypeReference<>(){});
        List<Label> labelList = labelStorage.findAll();

        assertAll(
                () -> assertEquals(labelList.get(0).getId(), labelResponseToList.get(0).getId()),
                () -> assertEquals(labelList.get(0).getName(), labelResponseToList.get(0).getName()),
                () -> assertEquals(labelList.get(0).getIssues().stream().findFirst().get().getId(), labelResponseToList.get(0).getIssueId()),

                () -> assertEquals(labelList.get(1).getId(), labelResponseToList.get(1).getId()),
                () -> assertEquals(labelList.get(1).getName(), labelResponseToList.get(1).getName()),
                () -> assertEquals(labelList.get(1).getIssues().stream().findFirst().get().getId(), labelResponseToList.get(1).getIssueId())
        );
    }

    @Test
    @DisplayName("Create label - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_labels.sql"})
    @SneakyThrows
    void createLabelOk() {
        LabelRequestTo labelRequestTo = new LabelRequestTo()
                .setIssueId(20L)
                .setName("New label name");

        MockHttpServletResponse response = mvc.perform(post(UriComponentsBuilder.fromUriString(URL_PREFIX+"/labels")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(labelRequestTo))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        LabelResponseTo labelResponseTo = objectMapper.readValue(response.getContentAsString(), LabelResponseTo.class);
        List<Label> labelList = labelStorage.findAll();
        Label label = labelStorage.findById(1L).get();

        assertAll(
                () -> assertEquals(3, labelList.size()),
                () -> assertEquals(labelRequestTo.getName(), label.getName()),
                () -> assertEquals(labelRequestTo.getIssueId(), label.getIssues().stream().findFirst().get().getId()),

                () -> assertEquals(label.getId(), labelResponseTo.getId()),
                () -> assertEquals(label.getName(), labelResponseTo.getName()),
                () -> assertEquals(label.getIssues().stream().findFirst().get().getId(), labelResponseTo.getIssueId())
        );
    }

    @Test
    @DisplayName("Delete label by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_labels.sql"})
    @SneakyThrows
    void deleteLabelByIdOk() {
        Long labelId = 10L;

        MockHttpServletResponse response = mvc.perform(delete(UriComponentsBuilder.fromUriString(URL_PREFIX+"/labels/{labelId}")
                        .buildAndExpand(labelId)
                        .toUriString()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals("", response.getContentAsString()),
                () -> assertTrue(labelStorage.findById(labelId).isEmpty())
        );
    }

    @Test
    @DisplayName("Update label by id - Ok")
    @Sql({"/sql/clean.sql", "/sql/insert_labels.sql"})
    @SneakyThrows
    void updateLabelByIdOk() {
        Long labelId = 10L;
        LabelRequestWithIdTo labelRequestWithIdTo = (LabelRequestWithIdTo) new LabelRequestWithIdTo()
                .setId(labelId)
                .setIssueId(20L)
                .setName("New label name");

        MockHttpServletResponse response = mvc.perform(put(UriComponentsBuilder.fromUriString(URL_PREFIX+"/labels")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(labelRequestWithIdTo))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        LabelResponseTo labelResponseTo = objectMapper.readValue(response.getContentAsString(), LabelResponseTo.class);
        List<Label> labelList = labelStorage.findAll();
        Label label = labelStorage.findById(labelId).get();

        assertAll(
                () -> assertEquals(2, labelList.size()),
                () -> assertEquals(labelRequestWithIdTo.getId(), label.getId()),
                () -> assertEquals(labelRequestWithIdTo.getName(), label.getName()),
                () -> assertEquals(labelRequestWithIdTo.getIssueId(), label.getIssues().stream().findFirst().get().getId()),

                () -> assertEquals(label.getId(), labelResponseTo.getId()),
                () -> assertEquals(label.getName(), labelResponseTo.getName()),
                () -> assertEquals(label.getIssues().stream().findFirst().get().getId(), labelResponseTo.getIssueId())
        );
    }
}
