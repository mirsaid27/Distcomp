package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.mapper.TweetMapper;
import by.harlap.jpa.model.Editor;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.service.EditorService;
import by.harlap.jpa.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetFacade {

    private final TweetService tweetService;
    private final EditorService editorService;
    private final TweetMapper tweetMapper;

    @Transactional(readOnly = true)
    public TweetResponseDto findById(Long id) {
        Tweet tweet = tweetService.findById(id);
        return tweetMapper.toTweetResponse(tweet);
    }

    @Transactional(readOnly = true)
    public List<TweetResponseDto> findAll() {
        List<Tweet> tweets = tweetService.findAll();

        return tweets.stream().map(tweetMapper::toTweetResponse).toList();
    }

    @Transactional
    public TweetResponseDto save(CreateTweetDto tweetRequest) {
        Tweet tweet = tweetMapper.toTweet(tweetRequest);

        Editor editor = editorService.findById(tweetRequest.getEditorId());
        tweet.setEditor(editor);

        Tweet savedTweet = tweetService.save(tweet);

        return tweetMapper.toTweetResponse(savedTweet);
    }

    @Transactional
    public TweetResponseDto update(UpdateTweetDto tweetRequest) {
        Tweet tweet = tweetService.findById(tweetRequest.getId());

        Tweet updatedTweet = tweetMapper.toTweet(tweetRequest, tweet);

        if (tweetRequest.getEditorId() != null) {
            Editor editor = editorService.findById(tweetRequest.getEditorId());
            updatedTweet.setEditor(editor);
        }

        return tweetMapper.toTweetResponse(tweetService.update(updatedTweet));
    }

    @Transactional
    public void delete(Long id) {
        tweetService.deleteById(id);
    }
}
