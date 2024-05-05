package com.example.rvlab1.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class ServiceException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ServiceException(String devMessage, ServiceErrorCode errorCode) {
        super(devMessage);
        errorMessage = new ErrorMessage(errorCode);
    }

    public ServiceException(String devMessage, ErrorMessage errorMessage) {
        super(devMessage);
        this.errorMessage = errorMessage;
    }
}
