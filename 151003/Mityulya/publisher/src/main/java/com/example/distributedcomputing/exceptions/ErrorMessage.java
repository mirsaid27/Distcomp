package com.example.distributedcomputing.exceptions;

import lombok.Data;

@Data
public class ErrorMessage {
    private Long errorCode;
    private String errorMessage;

    public ErrorMessage(Long errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
