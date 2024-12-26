package org.example.tweetapi.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseEntity {
    private Long id;
}
