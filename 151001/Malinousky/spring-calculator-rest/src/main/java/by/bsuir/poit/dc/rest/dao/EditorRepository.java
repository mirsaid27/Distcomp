package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EditorRepository extends JpaRepository<Editor, Long> {
    Optional<Editor> findByTweetId(
	@Param("tweet_id") long tweetId);
}