package by.bsuir.poit.dc.cassandra.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Name Surname
 * 
 */
@Table("comment_by_tweets")
@AllArgsConstructor
@Getter
@Setter
public class CommentByTweet {
    @PrimaryKeyColumn(
	name = "tweet_id",
	type = PrimaryKeyType.PARTITIONED,
	ordering = Ordering.ASCENDING)
    private Long tweetId;
    @PrimaryKeyColumn(
	name = "comment_id",
	type = PrimaryKeyType.CLUSTERED,
	ordinal = 1)
    private Long id;
    @Column("content")
    @NotNull
    private String content;
    @Column("status")
    private Short status;
}
