package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.model.request.CommentRequestTo;
import com.example.distributedcomputing.model.response.CommentResponseTo;
import com.example.distributedcomputing.repository.IssueRepository;
import com.example.distributedcomputing.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "commentCache")
public class CommentController {
    private final CommentService editorService;

    private final RestClient restClient;
    private final String uriBase = "http://localhost:24130/api/v1.0/comments";

    private final KafkaConsumer<String, CommentResponseTo> kafkaConsumer;

    private final KafkaSender kafkaSender;
    private final IssueRepository issueRepository;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";
    private final CacheManager cacheManager;

    @PostMapping
    public ResponseEntity<CommentResponseTo> saveComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                                         @RequestBody CommentRequestTo comment) throws NotFoundException {
        comment.setCountry(acceptLanguageHeader);
        comment.setMethod("POST");
        if (comment.getContent().length()<2 || comment.getContent().length()>100) {
            return ResponseEntity.status(404).body(new CommentResponseTo());
        }
        if (!issueRepository.existsById(comment.getIssueId())){
            return ResponseEntity.status(404).body(new CommentResponseTo());
        }
        kafkaSender.sendCustomMessage(comment, inTopic);
        var response = listenKafka();

        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        CommentRequestTo commentRequestTo = new CommentRequestTo();
        commentRequestTo.setMethod("DELETE");
        commentRequestTo.setId(id);
        kafkaSender.sendCustomMessage(commentRequestTo, inTopic);
        var response = listenKafka();
        if(response == null)
            throw new NotFoundException(404L, "Comment not found");

        Cache cache = cacheManager.getCache("comments");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        Cache cache = cacheManager.getCache("comments");
        if (cache != null) {
            CommentResponseTo cachedResponse = cache.get(id, CommentResponseTo.class);
            if (cachedResponse != null) {
                return ResponseEntity.status(200).body(cachedResponse);
            }
        }
        CommentRequestTo commentRequestTo = new CommentRequestTo();
        commentRequestTo.setMethod("GET");
        commentRequestTo.setId(id);
        kafkaSender.sendCustomMessage(commentRequestTo, inTopic);
        CommentResponseTo response = listenKafka();

        if (response != null) {
            if (cache != null) {
                cache.put(id, response);
            }
            return ResponseEntity.status(200).body(response);
        } else {
            throw new NotFoundException(404L, "Comment not found");
        }
    }

    @PutMapping()
    public ResponseEntity<CommentResponseTo> updateComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                                           @RequestBody CommentRequestTo comment) throws NotFoundException {
        Cache cache = cacheManager.getCache("comments");
        if (cache != null) {
            cache.evict(comment.getId());
        }
        comment.setCountry(acceptLanguageHeader);
        comment.setMethod("PUT");
        kafkaSender.sendCustomMessage(comment, inTopic);
        return ResponseEntity.status(200).body(listenKafka());
    }

    @GetMapping
    public ResponseEntity<List<?>> getComments(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    private CommentResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, CommentResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, CommentResponseTo> record : records) {

            String key = record.key();
            CommentResponseTo value = record.value();

            if (value == null) {
                throw new NotFoundException(404L, "Not found");
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
