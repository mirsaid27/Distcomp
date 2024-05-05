package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    Optional<Sticker> findByName(String name);

    @Query("select sticker from Sticker sticker left join " +
	       "TweetSticker tweetSticker on tweetSticker.sticker.id = sticker.id " +
	       "where tweetSticker.tweet.id = :tweet_id")
    List<Sticker> findByTweetId(@Param("tweet_id") long tweetId);
}