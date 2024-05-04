package org.example.discussionservice.service;

import org.example.discussionservice.kafkacl.request.KafkaRequestType;
import org.example.discussionservice.kafkacl.response.KafkaRequestProcessor;

import java.util.Map;

public interface MessageService {

    Map<KafkaRequestType, KafkaRequestProcessor> getProcessors();
}
