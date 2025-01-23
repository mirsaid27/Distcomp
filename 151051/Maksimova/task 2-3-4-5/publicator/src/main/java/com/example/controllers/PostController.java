package com.example.controllers;

import com.example.dto.PostRequestTo;
import com.example.dto.PostResponseTo;
import com.example.exceptions.NotFoundException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
@CacheConfig(cacheNames = "postCache")
public class PostController {
    @Autowired
    private RestClient restClient;
    @Autowired
    private KafkaConsumer<String, PostResponseTo> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;
    @Autowired
    private CacheManager cacheManager;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";
    private String uriBase = "http://localhost:24130/api/v1.0/posts";

    @GetMapping
    public ResponseEntity<List<?>> getPosts() {
        //kafkaSender.sendCustomMessage();
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseTo> getPost(@PathVariable Long id) throws NotFoundException {
        Cache cache = cacheManager.getCache("posts");
        if (cache != null) {
            PostResponseTo cachedResponse = cache.get(id, PostResponseTo.class);
            if (cachedResponse != null) {
                return ResponseEntity.status(200).body(cachedResponse);
            }
        }
        PostRequestTo postRequestTo = new PostRequestTo();
        postRequestTo.setMethod("GET");
        postRequestTo.setId(id);
        kafkaSender.sendCustomMessage(postRequestTo, inTopic);
        PostResponseTo response = listenKafka();

        if (response != null) {
            if (cache != null) {
                cache.put(id, response);
            }
            return ResponseEntity.status(200).body(response);
        } else {
            throw new NotFoundException("Post not found", 404L);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) throws NotFoundException {
        PostRequestTo postRequestTo = new PostRequestTo();
        postRequestTo.setMethod("DELETE");
        postRequestTo.setId(id);
        kafkaSender.sendCustomMessage(postRequestTo, inTopic);
        listenKafka();
        Cache cache = cacheManager.getCache("posts");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping
    public ResponseEntity<PostResponseTo> savePost(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody PostRequestTo post) throws NotFoundException {
        post.setCountry(acceptLanguageHeader);
        post.setMethod("POST");
        kafkaSender.sendCustomMessage(post, inTopic);
        return ResponseEntity.status(201).body(listenKafka());
    }

    @PutMapping()
    public ResponseEntity<PostResponseTo> updatePost(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody PostRequestTo post) throws NotFoundException {
        Cache cache = cacheManager.getCache("posts");
        if (cache != null) {
            cache.evict(post.getId());
        }
        post.setCountry(acceptLanguageHeader);
        post.setMethod("PUT");
        kafkaSender.sendCustomMessage(post, inTopic);
        return ResponseEntity.status(200).body(listenKafka());
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<?> getEditorByIssueId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byIssue/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }

    private PostResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, PostResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, PostResponseTo> record : records) {

            String key = record.key();
            PostResponseTo value = record.value();
            if (value == null) {
                throw new NotFoundException("Not found", 40400L);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("Received message: key = " + key + ", value = " + value +
                    ", offset = " + offset + ", partition = " + partition);

            return value;
        }
        return null;
    }
}
