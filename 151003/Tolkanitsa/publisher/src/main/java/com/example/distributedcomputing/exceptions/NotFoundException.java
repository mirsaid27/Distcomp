package com.example.distributedcomputing.exceptions;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private Long status;
    private String message;
}
