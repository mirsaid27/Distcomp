package com.example.discussion.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PostRequestWithIdTo extends PostRequestTo {
    private Long id;
}
