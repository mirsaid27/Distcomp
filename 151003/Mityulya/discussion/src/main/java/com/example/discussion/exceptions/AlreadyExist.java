package com.example.discussion.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AlreadyExist extends RuntimeException {
    private Long status;
    private String message;
}
