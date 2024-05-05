package com.example.discussion.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UpdateException extends RuntimeException {
    private String message;
    private Long status;
}
