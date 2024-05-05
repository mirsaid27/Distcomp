package org.example.discussionservice.kafkacl.response;

public interface KafkaRequestProcessor {
    String process(String argument);
}
