package com.example.publisherservice.service;

import com.example.publisherservice.dto.requestDto.PostRequestTo;
import com.example.publisherservice.dto.responseDto.PostResponseTo;
import com.example.publisherservice.kafkacl.request.KafkaRequestDto;
import com.example.publisherservice.kafkacl.request.KafkaRequestType;
import com.example.publisherservice.kafkacl.response.KafkaResponseDto;
import com.example.publisherservice.repository.PostRepository;
import com.example.publisherservice.util.exceptions.CassandraDatabaseException;
import com.example.publisherservice.util.ObjectMapperProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final StoryService storyService;

    private final String ERROR_MESSAGE = "ERROR_MESSAGE";

    private final Consumer<String, String> kafkaConsumer;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           StoryService storyService,
                           ConsumerFactory<String, String> consumerFactory,
                           KafkaTemplate<String, String> kafkaTemplate) {
        this.postRepository = postRepository;
        this.storyService = storyService;
        this.kafkaConsumer = consumerFactory.createConsumer();
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Cacheable(value = "post", key = "#id", unless = "#result==null")
    public PostResponseTo findDtoById(Long id) throws JsonProcessingException {

        // Create and send a request using id
        String randomUUID = UUID.randomUUID().toString();
        KafkaRequestDto request = new KafkaRequestDto(randomUUID,
                KafkaRequestType.GET, ObjectMapperProvider.writeObjectAsString(id));

        kafkaTemplate.send("inTopic", ObjectMapperProvider.writeObjectAsString(request));

        // Subscribes to outTopic and returns retrieved JSON object
        String value = tryRetrieveSubscriptionValue(randomUUID);

        // Handle empty value (means that discussion service couldn't find any entity Post table)
        if (value.equals(ERROR_MESSAGE)) {
            throw new CassandraDatabaseException("Error retrieving data from cassandra database.");
        }

        //parse JSON object into PostResponseTo object
        return ObjectMapperProvider.parseJsonStringToObject(value, PostResponseTo.class);
    }

    @Override
    public List<PostResponseTo> findAllDtos() {

        // Create and send a request using id
        String randomUUID = UUID.randomUUID().toString();
        KafkaRequestDto request = new KafkaRequestDto(randomUUID, KafkaRequestType.GET_ALL, "");

        kafkaTemplate.send("inTopic", ObjectMapperProvider.writeObjectAsString(request));

        // Subscribes to outTopic and returns retrieved JSON object
        String value = tryRetrieveSubscriptionValue(randomUUID);

        // Handle empty value (means that discussion service couldn't find any entity Post table)
        if (value.equals(ERROR_MESSAGE)) {
            throw new CassandraDatabaseException("Error retrieving data from cassandra database.");
        }

        // Parse JSON object into List<PostResponseTo> object
        return ObjectMapperProvider.parseJsonStringToList(value, PostResponseTo.class);

    }

    @Override
    public PostResponseTo create(PostRequestTo dto) {

        String randomUUID = UUID.randomUUID().toString();
        KafkaRequestDto request = new KafkaRequestDto(randomUUID,
                KafkaRequestType.POST, ObjectMapperProvider.writeObjectAsString(dto));

        kafkaTemplate.send("inTopic", ObjectMapperProvider.writeObjectAsString(request));

        // Subscribes to outTopic and returns retrieved JSON object
        String value = tryRetrieveSubscriptionValue(randomUUID);

        // Handle empty value (means that discussion service couldn't find any entity Post table)
        if (value.equals(ERROR_MESSAGE)) {
            throw new CassandraDatabaseException("Error retrieving data from cassandra database.");
        }

        //parse JSON object into PostResponseTo object
        return ObjectMapperProvider.parseJsonStringToObject(value, PostResponseTo.class);

    }

    @Override
    @CachePut(value = "post", key = "#dto.id", unless="#result==null")
    public PostResponseTo update(PostRequestTo dto) {

        String randomUUID = UUID.randomUUID().toString();
        KafkaRequestDto request = new KafkaRequestDto(randomUUID,
                KafkaRequestType.PUT, ObjectMapperProvider.writeObjectAsString(dto));

        kafkaTemplate.send("inTopic", ObjectMapperProvider.writeObjectAsString(request));

        // Subscribes to outTopic and returns retrieved JSON object
        String value = tryRetrieveSubscriptionValue(randomUUID);

        // Handle empty value (means that discussion service couldn't find any entity Post table)
        if (value.equals(ERROR_MESSAGE)) {
            throw new CassandraDatabaseException("Error retrieving data from cassandra database.");
        }

        //parse JSON object into PostResponseTo object
        return ObjectMapperProvider.parseJsonStringToObject(value, PostResponseTo.class);

    }

    @Override
    @CacheEvict(cacheNames = "post", key = "#id")
    public PostResponseTo deletePostById(Long id) {

        // Create and send a request using id
        String randomUUID = UUID.randomUUID().toString();
        KafkaRequestDto request = new KafkaRequestDto(randomUUID,
                KafkaRequestType.DELETE, ObjectMapperProvider.writeObjectAsString(id));

        kafkaTemplate.send("inTopic", ObjectMapperProvider.writeObjectAsString(request));

        // Subscribes to outTopic and returns retrieved JSON object
        String value = tryRetrieveSubscriptionValue(randomUUID);

        // Handle empty value (means that discussion service couldn't find any entity Post table)
        if (value.equals(ERROR_MESSAGE)) {
            throw new CassandraDatabaseException("Error retrieving data from cassandra database.");
        }

        //parse JSON object into PostResponseTo object
        return ObjectMapperProvider.parseJsonStringToObject(value, PostResponseTo.class);

    }

    String tryRetrieveSubscriptionValue(String requestId) {

        // We need to do repetitions between subscription data retrieving
        // because .poll(Duration) method of Kafka Consumer works in the way that
        // if there's a data in the topic, which is allowed to be read
        // .poll(Duration) method will grab only this data without any Duration.
        // As soon as correct value was retrieved no more repetitions will be made
        // That's why we need to do at least 2 repetitions.
        // Statistically ~99% of values will be retrieved correctly on 1st or 2nd repetitions.
        // 5 repetitions were set just in case.
        int REPETITIONS = 5;

        // Delay coefficient was set to increase delay between .poll(Duration) methods.
        int DELAY_COEFFICIENT = 50;

        String value = "";
        for (int i = 0; i < REPETITIONS; i++) {
            value = retrieveSubscriptionValue(requestId);
            if (!value.isEmpty()) {
                return value;
            }
            try {
                // If correct value wasn't retrieved on 1st or 2nd repetitions, there's kind of a
                // unexpected delay, so we need to give more time for message broker to handle this
                // delay properly.
                //
                // Here's a map for REPETITION -> SLEEP_TIME:
                // 0 -> 50ms
                // 1 -> 250ms
                // 2 -> 500ms
                // 3 -> 850ms
                // 4 -> 1300ms
                Thread.sleep(DELAY_COEFFICIENT * (i * i + 1));
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted Exception.");
            }
        }
        return value;
    }

    private String retrieveSubscriptionValue(String requestId) {
        // Wait for the response using a separate consumer group
        kafkaConsumer.subscribe(Collections.singleton("outTopic"));
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
        return retrieveConsumerRecordsLastValue(records, requestId);
    }

    private String retrieveConsumerRecordsLastValue
            (ConsumerRecords<String, String> records, String requestId) {

        if (!records.isEmpty()) {
            for (ConsumerRecord<String, String> record : records) {
                KafkaResponseDto kafkaResponseDto = ObjectMapperProvider
                        .parseJsonStringToObject(record.value(), KafkaResponseDto.class);
                if (kafkaResponseDto.getResponseId().equals(requestId)) {
                    return kafkaResponseDto.getValue();
                }
            }
        }
        return "";
    }

}
