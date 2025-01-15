package com.example.exceptions;

public class ResourceStateException extends ResourceException{
    public ResourceStateException(int code, String message) {
        super(code, message);
    }
}