package com.example.distributedcomputing.kafka;

import com.example.distributedcomputing.model.request.MessageRequestTo;
import com.example.distributedcomputing.model.response.MessageResponseTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MessageResponseToSerializer implements Serializer<MessageResponseTo>, Deserializer<MessageResponseTo> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, MessageResponseTo data) {
        try {
            System.out.println(objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8));
            return objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MessageResponseTo deserialize(String topic, byte[] data) {
        try {
            System.out.println(objectMapper.readValue(data, MessageRequestTo.class));
            return objectMapper.readValue(data, MessageResponseTo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
    }
}