package by.harlap.jpa.controller;

import by.harlap.jpa.BaseIntegrationTest;
import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.util.dto.tweet.request.CreateTweetDtoTestBuilder;
import by.harlap.jpa.util.dto.tweet.request.UpdateTweetDtoTestBuilder;
import by.harlap.jpa.util.dto.tweet.response.TweetResponseDtoTestBuilder;
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
@DisplayName("Tests for TweetController")
class TweetControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    
    @Nested
    class FindTweetByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            TweetResponseDto expected = TweetResponseDtoTestBuilder.tweet().build();

            mockMvc.perform(get("/api/v1.0/tweets/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            TweetResponseDto tweet = TweetResponseDtoTestBuilder.tweet().build();
            final List<TweetResponseDto> expected = List.of(tweet);

            mockMvc.perform(get("/api/v1.0/tweets", tweet.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateTweetDto request = CreateTweetDtoTestBuilder.tweet().build();

            mockMvc.perform(post("/api/v1.0/tweets")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class DeleteTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            final Long id = 1L;
            mockMvc.perform(delete("/api/v1.0/tweets/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateTweetDto request = UpdateTweetDtoTestBuilder.tweet().build();

            mockMvc.perform(put("/api/v1.0/tweets")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

    }
}