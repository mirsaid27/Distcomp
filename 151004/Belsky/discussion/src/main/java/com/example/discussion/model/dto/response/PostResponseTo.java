package com.example.discussion.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class PostResponseTo {
    private Long id;
    private Long issueId;
    private String content;
}
