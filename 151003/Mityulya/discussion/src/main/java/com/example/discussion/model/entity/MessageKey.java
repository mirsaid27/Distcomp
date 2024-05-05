package com.example.discussion.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageKey implements Serializable {
    private String country;
    private Long tweetId;
    private Long id;
}
