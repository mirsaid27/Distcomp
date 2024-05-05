package by.bsuir.repository;

import by.bsuir.entities.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long>, JpaSpecificationExecutor<Tweet> {
    boolean existsByTitle(String title);

    @Query("SELECT DISTINCT i FROM Tweet i " +
            "WHERE (:userLogin IS NULL OR i.user.login = :userLogin) " +
            "AND (:title IS NULL OR i.title LIKE %:title%) " +
            "AND (:content IS NULL OR i.content LIKE %:content%)")
    List<Tweet> findTweetsByParams(
                                   @Param("userLogin") String userLogin,
                                   @Param("title") String title,
                                   @Param("content") String content);

    Page<Tweet> findAll(Pageable pageable);
}
