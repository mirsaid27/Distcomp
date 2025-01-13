package com.example.discussion.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;

import java.io.Serializable;

@Table("tbl_comment")
@Data
public class Comment implements Serializable {

    public enum State {
        PENDING, APPROVE, DECLINE
    }

    @Column("country")
    private String country = "BY";

    @Column("tweetId")
    private Long tweetId;

    @PrimaryKey
    private Long id;

    @Column("content")
    private String content;
}
