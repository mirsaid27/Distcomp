package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateTweetStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.TweetDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
public interface TweetsService {
    TweetDto create(@Valid UpdateTweetDto dto);

    TweetDto update(long tweetId, @Valid UpdateTweetDto dto);

    TweetDto getById(long tweetsId);

    List<TweetDto> getByOffsetAndLimit(long offset, int limit);

    @Deprecated
    List<TweetDto> getAll();

    PresenceDto existsById(long tweetId);

    PresenceDto delete(long tweetId);

    void attachStickerById(long tweetId, @Valid UpdateTweetStickerDto dto);

    PresenceDto detachStickerById(long tweetId, long stickerId);

    List<TweetDto> getTweetByEditorId(long editorId);

    List<TweetDto> getTweetBySticker(String sticker);


    List<StickerDto> getStickersByTweetId(long tweetId);
}
