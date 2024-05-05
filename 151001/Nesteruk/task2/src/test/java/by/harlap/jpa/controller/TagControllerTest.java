package by.harlap.jpa.controller;

import by.harlap.jpa.BaseIntegrationTest;
import by.harlap.jpa.dto.request.CreateTagDto;
import by.harlap.jpa.dto.request.UpdateTagDto;
import by.harlap.jpa.dto.response.TagResponseDto;
import by.harlap.jpa.util.dto.tag.request.CreateTagDtoTestBuilder;
import by.harlap.jpa.util.dto.tag.request.UpdateTagDtoTestBuilder;
import by.harlap.jpa.util.dto.tag.response.CreateTagResponseTestBuilder;
import by.harlap.jpa.util.dto.tag.response.TagResponseDtoTestBuilder;
import by.harlap.jpa.util.dto.tag.response.UpdateTagResponseTestBuilder;
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
@DisplayName("Tests for TagController")
class TagControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Nested
    class FindTagByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            TagResponseDto expected = TagResponseDtoTestBuilder.tag().build();

            mockMvc.perform(get("/api/v1.0/tags/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            TagResponseDto tag = TagResponseDtoTestBuilder.tag().build();
            final List<TagResponseDto> expected = List.of(tag);

            mockMvc.perform(get("/api/v1.0/tags", tag.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateTagDto request = CreateTagDtoTestBuilder.tag().build();
            TagResponseDto expected = CreateTagResponseTestBuilder.tag().build();

            mockMvc.perform(post("/api/v1.0/tags")
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
            mockMvc.perform(delete("/api/v1.0/tags/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateTagDto request = UpdateTagDtoTestBuilder.tag().build();
            TagResponseDto expected = UpdateTagResponseTestBuilder.tag().build();

            mockMvc.perform(put("/api/v1.0/tags")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

    }
}