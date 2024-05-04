package com.example.discussion.config;

import com.example.discussion.exception.ErrorMessage;
import com.example.discussion.exception.ServiceErrorCode;
import com.example.discussion.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> serviceExceptionHandler(ServiceException e, WebRequest webRequest) {
        return ResponseEntity.status(e.getErrorMessage().getHttpStatus()).body(e.getErrorMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(Exception e, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(ServiceErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }
}
