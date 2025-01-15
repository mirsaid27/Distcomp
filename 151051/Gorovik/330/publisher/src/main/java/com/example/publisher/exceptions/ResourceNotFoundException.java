package com.example.exceptions;

public class ResourceNotFoundException extends ResourceException{
    public ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}