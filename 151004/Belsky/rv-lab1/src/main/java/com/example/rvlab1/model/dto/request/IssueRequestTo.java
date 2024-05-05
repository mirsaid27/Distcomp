package com.example.rvlab1.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IssueRequestTo {
    private Long userId;
    private String title;
    private String content;
}
