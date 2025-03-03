package com.bsuir.dc.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
            super(message);
        }
}
