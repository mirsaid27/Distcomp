package org.example.discussionservice.util.exception;

public class KafkaJsonParsingException extends RuntimeException {

    public KafkaJsonParsingException(String message) {
        super(message);
    }
}
