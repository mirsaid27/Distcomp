package com.example.rvlab1.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PostResponseTo {
    private Long id;
    private Long issueId;
    private String content;
}
