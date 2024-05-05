package by.bsuir.discussion.repositories;

import by.bsuir.discussion.domain.Post;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MapIdCassandraRepository<Post> {
    @AllowFiltering
    void deletePostByIssueIdAndId(Long id, Long uuid);
    @AllowFiltering //Bad solution, BUT generally we need to search all posts of certain tweet, so
                    //this search is redundant and that's why tweetId is a partition key
    Optional<Post> findPostById(Long id);
    Optional<Post> findPostByIssueIdAndId(Long id, Long uuid);
}