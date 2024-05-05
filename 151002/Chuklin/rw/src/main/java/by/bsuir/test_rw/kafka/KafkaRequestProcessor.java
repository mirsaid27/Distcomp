package by.bsuir.test_rw.kafka;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KafkaRequestProcessor {
    ResponseEntity<?> process(List<Object> args);
}
