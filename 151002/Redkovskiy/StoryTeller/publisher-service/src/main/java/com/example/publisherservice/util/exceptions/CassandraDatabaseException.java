package com.example.publisherservice.util.exceptions;

public class CassandraDatabaseException extends RuntimeException {

    public CassandraDatabaseException(String message) {
        super(message);
    }
}
