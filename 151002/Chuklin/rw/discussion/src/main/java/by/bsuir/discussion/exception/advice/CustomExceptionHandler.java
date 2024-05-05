package by.bsuir.discussion.exception.advice;

import by.bsuir.discussion.exception.model.dto_convert.ToConvertException;
import by.bsuir.discussion.exception.model.not_found.NotFoundException;
import by.bsuir.discussion.exception.model.response.ExceptionResponse;
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
        return generateResponseEntity(HttpStatus.FORBIDDEN, "Repository exception occured");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException exception) {
        return generateResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ToConvertException.class)
    public ResponseEntity<ExceptionResponse> handleToConvertingException(ToConvertException exception) {
        return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ExceptionResponse> generateResponseEntity(HttpStatus httpStatus, String message) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), message);
        return ResponseEntity.status(httpStatus).body(response);
    }


}
