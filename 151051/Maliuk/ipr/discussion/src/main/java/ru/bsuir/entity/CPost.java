package ru.bsuir.entity;


import org.springframework.data.cassandra.core.mapping.Table;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Column;

import java.io.Serializable;


@Data
@Table("tbl_post")
public class CPost implements Serializable {

    public enum State {
        PENDING, APPROVE, DECLINE
    }
    @PrimaryKey
    private Long id ;

    @Column("storyId")
    private Long tweetId;

    @Column("content")
    private String content;

    @Column("country")
    private String country = "BY";
}