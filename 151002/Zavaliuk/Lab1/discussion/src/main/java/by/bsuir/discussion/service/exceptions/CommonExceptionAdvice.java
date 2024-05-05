package by.bsuir.discussion.service.exceptions;

import by.bsuir.discussion.model.response.ErrorResponseTo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CommonExceptionAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseTo> catchNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
    }

    @ExceptionHandler({ResourceStateException.class})
    public ResponseEntity<ErrorResponseTo> catchStateException(ResourceStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getError());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseTo> catchValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOf(e));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorResponseTo> catchThrowable(Throwable t) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOf(t));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseTo> catchDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseTo(
                40387,
                "Invalid request data",
                null
        ));
    }

    private ErrorResponseTo errorOf(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : e.getAllErrors()) {
            String message;
            if (error instanceof FieldError fieldError) {
                message = "Field " + fieldError.getField() + " : " + fieldError.getDefaultMessage();
            } else {
                message = error.toString();
            }
            messages.add(message);
        }
        String errorMessage = "Some parameters in your request doesn't meet validation rules";
        return new ErrorResponseTo(HttpStatus.BAD_REQUEST.value() * 100 + 77, errorMessage, messages.toArray(String[]::new));
    }

    private ErrorResponseTo errorOf(Throwable t) {
        String message = "Provided request can't be handled by server";
        return new ErrorResponseTo(HttpStatus.BAD_REQUEST.value() * 100 + 78, message, new String[]{t.getMessage()});
    }
}
