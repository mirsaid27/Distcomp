package com.example.discussion.controller;

import com.example.discussion.config.JsonMapper;
import com.example.discussion.config.LongUtils;
import com.example.discussion.config.kafka.KafkaBatch;
import com.example.discussion.config.kafka.KafkaExtractor;
import com.example.discussion.exception.ServiceErrorCode;
import com.example.discussion.exception.ServiceException;
import com.example.discussion.mapper.PostMapper;
import com.example.discussion.model.dto.request.PostRequestTo;
import com.example.discussion.model.dto.request.PostRequestWithIdTo;
import com.example.discussion.model.dto.response.PostResponseTo;
import com.example.discussion.model.service.Post;
import com.example.discussion.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/api/v1.0/")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final KafkaExtractor kafkaExtractor;
    private final PostService postService;
    private final PostMapper postMapper;
    private final JsonMapper jsonMapper;

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponseTo>> getAllPosts() {
        return ResponseEntity.ok(postService.getAll().stream().map(postMapper::mapToResponseTo).toList());
    }

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseTo> createPost(@RequestBody PostRequestTo postRequestTo) {
        Post post = postService.savePost(postMapper.mapToBO(postRequestTo));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.mapToResponseTo(post));
    }

    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable("postId") Long postId) {
        Post post = postService.findById(postId);
        postService.deletePost(post);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseTo> getPostById(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        return ResponseEntity.ok(postMapper.mapToResponseTo(post));
    }

    @PutMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseTo> updatePostById(@RequestBody PostRequestWithIdTo postRequestTo) {
        Post post = postService.findById(postRequestTo.getId());
        postMapper.updatePostRequestToToPostBo(postRequestTo, post);
        post = postService.savePost(post);
        return ResponseEntity.ok(postMapper.mapToResponseTo(post));
    }

    @KafkaListener(topics = "InTopic", containerFactory = "requestListenerContainerFactory")
    @SendTo
    public String listen(ConsumerRecord<String, String> record) {
        try {
            log.info("Request: {}", record.value());
            Object response = switch (record.key()) {
                case "getAllPosts" -> {
                    yield postService.getAll().stream().map(postMapper::mapToResponseTo).toList();
                }
                case "deletePostById" -> {
                    KafkaBatch kafkaBatch = kafkaExtractor.extract(record.value(), String.class);
                    Long postId = LongUtils.valueOf(kafkaBatch.getParams().get("postId"));
                    Post post = postService.findById(postId);
                    postService.deletePost(post);
                    yield null;
                }
                case "getPostById" -> {
                    KafkaBatch kafkaBatch = kafkaExtractor.extract(record.value(), String.class);
                    Long postId = LongUtils.valueOf(kafkaBatch.getParams().get("postId"));
                    Post post = postService.findById(postId);
                    yield postMapper.mapToResponseTo(post);
                }
                case "createPost" -> {
                    KafkaBatch kafkaBatch = kafkaExtractor.extract(record.value(), PostRequestTo.class);
                    PostRequestTo request = (PostRequestTo) kafkaBatch.getData();
                    Post post = postService.savePost(postMapper.mapToBO(request)
                            .setId((long) Math.abs(new Random().nextInt()))
                    );
                    yield postMapper.mapToResponseTo(post);
                }
                case "updatePostById" -> {
                    KafkaBatch kafkaBatch = kafkaExtractor.extract(record.value(), PostRequestWithIdTo.class);
                    PostRequestWithIdTo postRequestTo = (PostRequestWithIdTo) kafkaBatch.getData();
                    Post post = postService.findById(postRequestTo.getId());
                    postMapper.updatePostRequestToToPostBo(postRequestTo, post);
                    post = postService.savePost(post);
                    yield postMapper.mapToResponseTo(post);
                }
                default -> null;
            };
            return jsonMapper.toString(new KafkaBatch().setData(response));
        } catch (ServiceException e) {
            return jsonMapper.toString(new KafkaBatch().setData(e.getErrorMessage()));
        } catch (Exception e) {
            var serviceException = new ServiceException("Какая-то ошибка", ServiceErrorCode.INTERNAL_SERVER_ERROR);
            return jsonMapper.toString(new KafkaBatch().setData(serviceException.getErrorMessage()));
        }
    }
}
