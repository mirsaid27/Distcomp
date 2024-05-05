package by.bsuir.test_rw.exception.advice;

import by.bsuir.test_rw.exception.model.dto_convert.ToConvertException;
import by.bsuir.test_rw.exception.model.not_found.NotFoundException;
import by.bsuir.test_rw.exception.model.response.ExceptionResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({DataAccessException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ExceptionResponse> handleDataAccessException() {
        return generateResponse(HttpStatus.FORBIDDEN, "Data layer exception");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(ToConvertException.class)
    public ResponseEntity<String> handleToConvert(ToConvertException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> generateResponse(HttpStatus status, String message) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), message);
        return ResponseEntity.status(status).body(response);
    }

}
