package org.example.discussion.dto.responseDto;

import lombok.Data;

@Data
public class MessageResponseTo {
    private long id;
    private long storyId;
    private String content;
}
