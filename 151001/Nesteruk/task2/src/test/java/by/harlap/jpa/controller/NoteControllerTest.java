package by.harlap.jpa.controller;

import by.harlap.jpa.BaseIntegrationTest;
import by.harlap.jpa.dto.request.CreateNoteDto;
import by.harlap.jpa.dto.request.UpdateNoteDto;
import by.harlap.jpa.dto.response.NoteResponseDto;
import by.harlap.jpa.util.dto.note.request.CreateNoteDtoTestBuilder;
import by.harlap.jpa.util.dto.note.request.UpdateNoteDtoTestBuilder;
import by.harlap.jpa.util.dto.note.response.CreateNoteResponseTestBuilder;
import by.harlap.jpa.util.dto.note.response.NoteResponseDtoTestBuilder;
import by.harlap.jpa.util.dto.note.response.UpdateNoteResponseTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for NoteController")
class NoteControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Nested
    class FindNoteByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            NoteResponseDto expected = NoteResponseDtoTestBuilder.note().build();

            mockMvc.perform(get("/api/v1.0/notes/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            NoteResponseDto note = NoteResponseDtoTestBuilder.note().build();
            final List<NoteResponseDto> expected = List.of(note);

            mockMvc.perform(get("/api/v1.0/notes", note.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateNoteDto request = CreateNoteDtoTestBuilder.note().build();
            NoteResponseDto expected = CreateNoteResponseTestBuilder.note().build();

            mockMvc.perform(post("/api/v1.0/notes")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class DeleteTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            final Long id = 1L;
            mockMvc.perform(delete("/api/v1.0/notes/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateNoteDto request = UpdateNoteDtoTestBuilder.note().build();
            NoteResponseDto expected = UpdateNoteResponseTestBuilder.note().build();

            mockMvc.perform(put("/api/v1.0/notes")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

    }
}