package com.example.discussion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseTo implements Serializable {
    private String country = "BY";
    private Long tweetId;
    private Long id;
    private String content;
    private boolean errorExist;
}
