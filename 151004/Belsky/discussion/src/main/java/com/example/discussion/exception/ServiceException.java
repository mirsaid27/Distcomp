package com.example.discussion.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ServiceException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ServiceException(String devMessage, ServiceErrorCode errorCode) {
        super(devMessage);
        errorMessage = new ErrorMessage(errorCode);
    }
}
