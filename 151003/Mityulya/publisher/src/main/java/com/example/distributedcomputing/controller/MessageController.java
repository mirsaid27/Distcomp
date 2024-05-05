package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.model.request.MessageRequestTo;
import com.example.distributedcomputing.model.response.MessageResponseTo;
import com.example.distributedcomputing.service.MessageService;
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
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "messageCache")
public class MessageController {
    private final MessageService editorService;

    private final RestClient restClient;
    private final String uriBase = "http://localhost:24130/api/v1.0/messages";

    private final KafkaConsumer<String, MessageResponseTo> kafkaConsumer;

    private final KafkaSender kafkaSender;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";
    private final CacheManager cacheManager;

    @PostMapping
    public ResponseEntity<MessageResponseTo> saveComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                                         @RequestBody MessageRequestTo comment) throws NotFoundException {
        comment.setCountry(acceptLanguageHeader);
        comment.setMethod("POST");
        kafkaSender.sendCustomMessage(comment, inTopic);
        return ResponseEntity.status(201).body(listenKafka());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        MessageRequestTo commentRequestTo = new MessageRequestTo();
        commentRequestTo.setMethod("DELETE");
        commentRequestTo.setId(id);
        kafkaSender.sendCustomMessage(commentRequestTo, inTopic);
        listenKafka();
        Cache cache = cacheManager.getCache("messages");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getComment(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        Cache cache = cacheManager.getCache("messages");
        if (cache != null) {
            MessageResponseTo cachedResponse = cache.get(id, MessageResponseTo.class);
            if (cachedResponse != null) {
                return ResponseEntity.status(200).body(cachedResponse);
            }
        }
        MessageRequestTo commentRequestTo = new MessageRequestTo();
        commentRequestTo.setMethod("GET");
        commentRequestTo.setId(id);
        kafkaSender.sendCustomMessage(commentRequestTo, inTopic);
        MessageResponseTo response = listenKafka();

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
    public ResponseEntity<MessageResponseTo> updateComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                                           @RequestBody MessageRequestTo comment) throws NotFoundException {
        Cache cache = cacheManager.getCache("messages");
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

    private MessageResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, MessageResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, MessageResponseTo> record : records) {

            String key = record.key();
            MessageResponseTo value = record.value();
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
