package by.bsuir.poit.dc.cassandra.dao;

import by.bsuir.poit.dc.cassandra.model.CommentByTweet;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Name Surname
 * 
 */
public interface CommentByTweetRepository extends MapIdCassandraRepository<CommentByTweet> {
    List<CommentByTweet> findByTweetId(long tweetId);

    Optional<CommentByTweet> findByIdAndTweetId(long id, long tweetId);

    void deleteByIdAndTweetId(long id, long tweetId);
}
