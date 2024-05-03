package com.example.rvlab1.restclient.impl;

import com.example.rvlab1.config.JsonMapper;
import com.example.rvlab1.config.kafka.KafkaBatch;
import com.example.rvlab1.config.kafka.KafkaExtractor;
import com.example.rvlab1.exception.ErrorMessage;
import com.example.rvlab1.exception.ServiceErrorCode;
import com.example.rvlab1.exception.ServiceException;
import com.example.rvlab1.model.dto.request.PostRequestTo;
import com.example.rvlab1.model.dto.request.PostRequestWithIdTo;
import com.example.rvlab1.model.dto.response.PostResponseTo;
import com.example.rvlab1.restclient.PostApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostApiClientImpl implements PostApiClient {
    private final JsonMapper jsonMapper;
    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    private final KafkaExtractor kafkaExtractor;

    @Value("${rest-client.post.url}")
    private String postUrl;

    @Override
    public List<PostResponseTo> getAllPosts() {
        KafkaBatch kafkaBatch = new KafkaBatch();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("InTopic", "getAllPosts", jsonMapper.toString(kafkaBatch));
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "OutTopic".getBytes()));
        try {
            var response = kafkaTemplate.sendAndReceive(producerRecord, Duration.ofSeconds(5)).get();
            log.info("Response: {}", response.value());
            KafkaBatch result = kafkaExtractor.extractList(response.value(), PostResponseTo.class);
            if (result.getData() instanceof ErrorMessage e) {
                throw new ServiceException("", e);
            }
            return (List<PostResponseTo>) result.getData();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Какая-то ошибка", ServiceErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CachePut(value = "posts", key = "#result.id")
    public PostResponseTo createPost(PostRequestTo postRequestTo) {
        KafkaBatch kafkaBatch = new KafkaBatch()
                .setData(postRequestTo);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("InTopic", "createPost", jsonMapper.toString(kafkaBatch));
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "OutTopic".getBytes()));
        try {
            var response = kafkaTemplate.sendAndReceive(producerRecord, Duration.ofSeconds(5)).get();
            log.info("Response: {}", response.value());
            KafkaBatch result = kafkaExtractor.extract(response.value(), PostResponseTo.class);
            if (result.getData() instanceof ErrorMessage e) {
                throw new ServiceException("", e);
            }
            return (PostResponseTo) result.getData();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Какая-то ошибка", ServiceErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CacheEvict(value = "posts", key = "#postId")
    public void deletePostById(Long postId) {
        KafkaBatch kafkaBatch = new KafkaBatch()
                .setParams(Map.of("postId", postId));
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("InTopic", "deletePostById", jsonMapper.toString(kafkaBatch));
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "OutTopic".getBytes()));
        try {
            var response = kafkaTemplate.sendAndReceive(producerRecord, Duration.ofSeconds(5)).get();
            KafkaBatch result = kafkaExtractor.extract(response.value(), String.class);
            if (result.getData() instanceof ErrorMessage e) {
                throw new ServiceException("", e);
            }
            log.info("Response: {}", response.value());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Какая-то ошибка", ServiceErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "posts", key = "#postId")
    public PostResponseTo getPostById(Long postId) {
        KafkaBatch kafkaBatch = new KafkaBatch()
                .setParams(Map.of("postId", postId));
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("InTopic", "getPostById", jsonMapper.toString(kafkaBatch));
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "OutTopic".getBytes()));
        try {
            var response = kafkaTemplate.sendAndReceive(producerRecord, Duration.ofSeconds(5)).get();
            log.info("Response: {}", response.value());
            KafkaBatch result = kafkaExtractor.extract(response.value(), PostResponseTo.class);
            if (result.getData() instanceof ErrorMessage e) {
                throw new ServiceException("", e);
            }
            return (PostResponseTo) result.getData();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Какая-то ошибка", ServiceErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CachePut(value = "posts", key = "#result.id")
    public PostResponseTo updatePost(PostRequestWithIdTo postRequestTo) {
        KafkaBatch kafkaBatch = new KafkaBatch()
                .setData(postRequestTo);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("InTopic", "updatePostById", jsonMapper.toString(kafkaBatch));
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "OutTopic".getBytes()));
        try {
            var response = kafkaTemplate.sendAndReceive(producerRecord, Duration.ofSeconds(5)).get();
            log.info("Response: {}", response.value());
            KafkaBatch result = kafkaExtractor.extract(response.value(), PostResponseTo.class);
            if (result.getData() instanceof ErrorMessage e) {
                throw new ServiceException("", e);
            }
            return (PostResponseTo) result.getData();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Какая-то ошибка", ServiceErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
