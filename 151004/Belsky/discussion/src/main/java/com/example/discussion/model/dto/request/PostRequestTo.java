package com.example.discussion.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PostRequestTo {
    private Long issueId;
    private String content;
}
