package com.example.publicator.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerRequestTo {
    int id;
    @Size(min = 2, max = 32)
    String name;
    int tweetId;
}
