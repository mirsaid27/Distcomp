package org.example.discussionservice.rest.provider;

import org.example.discussionservice.util.exception.DataDuplicationException;
import org.example.discussionservice.util.exception.DataNotFoundException;
import org.example.discussionservice.util.ExceptionResponse;
import org.example.discussionservice.util.exception.KafkaJsonParsingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionProvider {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDataNotFoundException(DataNotFoundException e) {
        return generateResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DataDuplicationException.class)
    public ResponseEntity<ExceptionResponse> handleDataDuplicationException(DataDuplicationException e) {
        return generateResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(KafkaJsonParsingException.class)
    public ResponseEntity<ExceptionResponse> handleKafkaJsonParsingException(KafkaJsonParsingException e) {
        return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }


    private ResponseEntity<ExceptionResponse> generateResponseEntity(HttpStatus httpStatus, String message) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), message);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
