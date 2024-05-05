package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.Tweet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findAllByEditorId(long id);

    @EntityGraph(attributePaths = {"comments"})
    Optional<Tweet> findWithCommentsById(long id);

    @EntityGraph(attributePaths = {"stickers"})
    Optional<Tweet> findWithStickersById(long id);
}