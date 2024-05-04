package org.example.discussionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("tbl_post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @PrimaryKey
    private PostId postId;

    private String content;

}
