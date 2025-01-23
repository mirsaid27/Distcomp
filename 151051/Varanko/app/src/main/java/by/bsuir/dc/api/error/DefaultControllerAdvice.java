package by.bsuir.dc.api.error;

import by.bsuir.dc.api.base.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class DefaultControllerAdvice {

    @ExceptionHandler(
        {
            HttpMessageNotReadableException.class,
            NullPointerException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestPartException.class,
            UnsatisfiedServletRequestParameterException.class,
            MissingServletRequestParameterException.class
        }
    )
    public ResponseEntity<ApiError> handleBadRequestException(Exception exception) {
        String exceptionMessage = exception.getMessage();
        if (exception instanceof MethodArgumentNotValidException notValidException) {
            FieldError fieldError = notValidException.getFieldError();
            exceptionMessage = "%s  %s".formatted(Objects.requireNonNull(fieldError).getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()+ ": " + exceptionMessage));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        String.valueOf(HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND.getReasonPhrase() + ": " + exception.getMessage()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleForbiddenException(Exception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        String.valueOf(HttpStatus.FORBIDDEN.value()),
                        HttpStatus.FORBIDDEN.getReasonPhrase() + ": " + exception.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + ": " + exception.getMessage()));
    }

}