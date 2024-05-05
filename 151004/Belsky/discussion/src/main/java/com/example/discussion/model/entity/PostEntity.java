package com.example.discussion.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Accessors(chain = true)
@Table("tblPost")
public class PostEntity {
    @PrimaryKey
    private Long id;
    private Long issueId;
    private String content;
}
