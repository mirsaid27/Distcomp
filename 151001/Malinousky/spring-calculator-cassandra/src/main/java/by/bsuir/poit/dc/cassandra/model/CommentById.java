package by.bsuir.poit.dc.cassandra.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Name Surname
 * 
 */
@Table("comment_by_id")
@AllArgsConstructor
@Getter
@Setter
public class CommentById {
    @PrimaryKeyColumn(
	name = "comment_id",
	type = PrimaryKeyType.PARTITIONED,
	ordinal = 0)
    private long id;
    @Column("tweet_id")
    private long tweetId;
    @Column("content")
    @NotNull
    private String content;
    @Column("status")
    private Short status;
//    @Column("status")
//    private long status;
}
