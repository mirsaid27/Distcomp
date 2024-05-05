package by.bsuir.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage2> catchValidationException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage2(40001L, "Bad request!"));
    }

    @ExceptionHandler(NotFoundException2.class)
    public ResponseEntity<ErrorMessage2> catchNotFoundException(NotFoundException2 exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage2(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(UpdateException2.class)
    public ResponseEntity<ErrorMessage2> catchUpdateException(UpdateException2 exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage2(exception.getStatus(), exception.getMessage()));
    }
    @ExceptionHandler(DeleteException2.class)
    public ResponseEntity<ErrorMessage2> catchDeleteException(DeleteException2 exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage2(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(DuplicationException2.class)
    public ResponseEntity<ErrorMessage2> catchDuplicationException(DuplicationException2 exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage2(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorMessage2> catchNumberInsteadOfStringExceptionException(JsonParseException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage2(40006L, exception.getMessage()));
    }
}
