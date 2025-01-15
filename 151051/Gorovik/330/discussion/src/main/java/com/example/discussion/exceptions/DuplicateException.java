package com.example.exceptions;

import com.example.response.ErrorResponseTo;

public class DuplicateException extends ResourceException{
    public DuplicateException(int code, String message)
    {
        super(code, message);
    }
}