package by.harlap.jpa.controller;

import by.harlap.jpa.BaseIntegrationTest;
import by.harlap.jpa.dto.request.CreateEditorDto;
import by.harlap.jpa.dto.request.UpdateEditorDto;
import by.harlap.jpa.dto.response.EditorResponseDto;
import by.harlap.jpa.util.dto.editor.request.CreateEditorDtoTestBuilder;
import by.harlap.jpa.util.dto.editor.request.UpdateEditorDtoTestBuilder;
import by.harlap.jpa.util.dto.editor.response.EditorResponseDtoTestBuilder;
import by.harlap.jpa.util.dto.editor.response.CreateEditorResponseDtoTestBuilder;
import by.harlap.jpa.util.dto.editor.response.UpdateEditorResponseTestBuilder;
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
@DisplayName("Tests for EditorController")
class EditorControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Nested
    class FindEditorByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            EditorResponseDto expected = EditorResponseDtoTestBuilder.editor().build();

            mockMvc.perform(get("/api/v1.0/editors/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            EditorResponseDto editor = EditorResponseDtoTestBuilder.editor().build();
            final List<EditorResponseDto> expected = List.of(editor);

            mockMvc.perform(get("/api/v1.0/editors", editor.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateEditorDto request = CreateEditorDtoTestBuilder.editor().build();
            EditorResponseDto expected = CreateEditorResponseDtoTestBuilder.editor().build();

            mockMvc.perform(post("/api/v1.0/editors")
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
            mockMvc.perform(delete("/api/v1.0/editors/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateEditorDto request = UpdateEditorDtoTestBuilder.editor().build();
            EditorResponseDto expected = UpdateEditorResponseTestBuilder.editor().build();

            mockMvc.perform(put("/api/v1.0/editors")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

    }
}