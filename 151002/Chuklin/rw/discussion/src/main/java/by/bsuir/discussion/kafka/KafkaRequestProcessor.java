package by.bsuir.discussion.kafka;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KafkaRequestProcessor {
    ResponseEntity<?> process(List<String> args);
}
