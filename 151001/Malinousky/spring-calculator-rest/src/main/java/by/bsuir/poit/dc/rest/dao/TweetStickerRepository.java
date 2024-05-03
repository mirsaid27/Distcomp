package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.TweetSticker;
import by.bsuir.poit.dc.rest.model.TweetStickerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetStickerRepository extends JpaRepository<TweetSticker, TweetStickerId> {
    boolean existsByTweetIdAndStickerId(long tweetId, long stickerId);

    void deleteByTweetIdAndStickerId(long tweetId, long stickerId);

    List<TweetSticker> findAllByStickerName(String sticker);
}