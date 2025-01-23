package com.example.discussion.model;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tbl_post")
public class Post {
    @PrimaryKeyColumn(name = "post_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private BigInteger id;

    @PrimaryKeyColumn(name = "post_newsid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private BigInteger newsId;

    @PrimaryKeyColumn(name = "post_country", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String post_country;

    @Column("post_content")
    private String content;
}