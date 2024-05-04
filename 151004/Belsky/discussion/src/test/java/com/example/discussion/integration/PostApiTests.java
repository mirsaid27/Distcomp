package com.example.discussion.integration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.example.discussion.DiscussionApplication;
import com.example.discussion.model.dto.request.PostRequestTo;
import com.example.discussion.model.dto.request.PostRequestWithIdTo;
import com.example.discussion.model.dto.response.PostResponseTo;
import com.example.discussion.model.entity.PostEntity;
import com.example.discussion.model.service.Post;
import com.example.discussion.repository.PostRepository;
import com.example.discussion.storage.PostStorage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DiscussionApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Testcontainers
public class PostApiTests {
    private static final String URL_PREFIX = "/api/v1.0";
    @Container
    public static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:latest")
            .withExposedPorts(9042);

    private static CqlSession session;

    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.cassandra.keyspace-name", "distcomp");
        System.setProperty("spring.cassandra.contact-points", cassandra.getContainerIpAddress());
        System.setProperty("spring.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));

        session = CqlSession.builder()
                .addContactPoint(cassandra.getContactPoint())
                .withLocalDatacenter(cassandra.getLocalDatacenter())
                .build();
        session.execute("CREATE KEYSPACE IF NOT EXISTS distcomp WITH replication = {'class':'SimpleStrategy','replication_factor':'1'};");
    }

    @BeforeEach
    public void beforeEach() {
        postRepository.save(new PostEntity()
                .setId(10L)
                .setIssueId(10L)
                .setContent("Post content 1")
        );
        postRepository.save(new PostEntity()
                .setId(20L)
                .setIssueId(20L)
                .setContent("Post content 2")
        );
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostStorage postStorage;

    @Test
    @DisplayName("Get post by id - Ok")
    @SneakyThrows
    void getPostByIdOk() {
        Long postId = 10L;

        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/posts/{postId}")
                        .buildAndExpand(postId)
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        PostResponseTo postResponseTo = objectMapper.readValue(response.getContentAsString(), PostResponseTo.class);
        Post post = postStorage.findById(postId).get();

        assertAll(
                () -> assertEquals(post.getId(), postResponseTo.getId()),
                () -> assertEquals(post.getIssueId(), postResponseTo.getIssueId()),
                () -> assertEquals(post.getContent(), postResponseTo.getContent())
        );
    }

    @Test
    @DisplayName("Get all posts - Ok")
    @SneakyThrows
    void getAllPostsOk() {
        MockHttpServletResponse response = mvc.perform(get(UriComponentsBuilder.fromUriString(URL_PREFIX+"/posts")
                        .toUriString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<PostResponseTo> postResponseToList = objectMapper.readValue(response.getContentAsString(), new TypeReference<>(){});
        List<Post> postList = postStorage.findAll();

        assertAll(
                () -> assertEquals(postList.get(0).getId(), postResponseToList.get(0).getId()),
                () -> assertEquals(postList.get(0).getIssueId(), postResponseToList.get(0).getIssueId()),
                () -> assertEquals(postList.get(0).getContent(), postResponseToList.get(0).getContent()),

                () -> assertEquals(postList.get(1).getId(), postResponseToList.get(1).getId()),
                () -> assertEquals(postList.get(1).getIssueId(), postResponseToList.get(1).getIssueId()),
                () -> assertEquals(postList.get(1).getContent(), postResponseToList.get(1).getContent())
        );
    }

    @Test
    @DisplayName("Create post - Ok")
    @SneakyThrows
    void createPostOk() {
        PostRequestTo postRequestTo = new PostRequestTo()
                .setIssueId(10L)
                .setContent("New post content");

        MockHttpServletResponse response = mvc.perform(post(UriComponentsBuilder.fromUriString(URL_PREFIX+"/posts")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(postRequestTo))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        PostResponseTo postResponseTo = objectMapper.readValue(response.getContentAsString(), PostResponseTo.class);
        List<Post> postList = postStorage.findAll();
        Post post = postList.stream().filter(post1 -> !Objects.equals(10L, post1.getId()) && !Objects.equals(20L, post1.getId())).findFirst().get();

        assertAll(
                () -> assertEquals(3, postList.size()),
                () -> assertEquals(postRequestTo.getIssueId(), post.getIssueId()),
                () -> assertEquals(postRequestTo.getContent(), post.getContent()),

                () -> assertEquals(post.getId(), postResponseTo.getId()),
                () -> assertEquals(post.getIssueId(), postResponseTo.getIssueId()),
                () -> assertEquals(post.getContent(), postResponseTo.getContent())
        );
    }

    @Test
    @DisplayName("Delete post by id - Ok")
    @SneakyThrows
    void deletePostByIdOk() {
        Long postId = 10L;

        MockHttpServletResponse response = mvc.perform(delete(UriComponentsBuilder.fromUriString(URL_PREFIX+"/posts/{postId}")
                        .buildAndExpand(postId)
                        .toUriString()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals("", response.getContentAsString()),
                () -> assertTrue(postStorage.findById(postId).isEmpty())
        );
    }

    @Test
    @DisplayName("Update post by id - Ok")
    @SneakyThrows
    void updatePostByIdOk() {
        Long postId = 10L;
        PostRequestWithIdTo postRequestWithIdTo = (PostRequestWithIdTo) new PostRequestWithIdTo()
                .setId(10L)
                .setIssueId(20L)
                .setContent("New Post Content");

        MockHttpServletResponse response = mvc.perform(put(UriComponentsBuilder.fromUriString(URL_PREFIX+"/posts")
                        .toUriString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(postRequestWithIdTo))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        PostResponseTo postResponseTo = objectMapper.readValue(response.getContentAsString(), PostResponseTo.class);
        List<Post> postList = postStorage.findAll();
        Post post = postStorage.findById(postId).get();

        assertAll(
                () -> assertEquals(2, postList.size()),
                () -> assertEquals(postRequestWithIdTo.getId(), post.getId()),
                () -> assertEquals(postRequestWithIdTo.getIssueId(), post.getIssueId()),
                () -> assertEquals(postRequestWithIdTo.getContent(), post.getContent()),

                () -> assertEquals(post.getId(), postResponseTo.getId()),
                () -> assertEquals(post.getIssueId(), postResponseTo.getIssueId()),
                () -> assertEquals(post.getContent(), postResponseTo.getContent())
        );
    }

}
