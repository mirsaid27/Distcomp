package com.example.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StickerRequestTo {
    @GeneratedValue
    private BigInteger id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
}
