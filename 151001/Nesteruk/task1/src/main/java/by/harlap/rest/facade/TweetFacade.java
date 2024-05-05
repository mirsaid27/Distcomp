package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateTweetDto;
import by.harlap.rest.dto.request.UpdateTweetDto;
import by.harlap.rest.dto.response.TweetResponseDto;
import by.harlap.rest.mapper.TweetMapper;
import by.harlap.rest.model.Tweet;
import by.harlap.rest.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetFacade {

    private final TweetService tweetService;
    private final TweetMapper tweetMapper;

    public TweetResponseDto findById(Long id) {
        Tweet tweet = tweetService.findById(id);
        return tweetMapper.toTweetResponse(tweet);
    }

    public List<TweetResponseDto> findAll() {
        List<Tweet> tweets = tweetService.findAll();

        return tweets.stream().map(tweetMapper::toTweetResponse).toList();
    }

    public TweetResponseDto save(CreateTweetDto tweetRequest) {
        Tweet tweet = tweetMapper.toTweet(tweetRequest);

        Tweet savedTweet = tweetService.save(tweet);

        return tweetMapper.toTweetResponse(savedTweet);
    }

    public TweetResponseDto update(UpdateTweetDto tweetRequest) {
        Tweet tweet = tweetService.findById(tweetRequest.getId());

        Tweet updatedTweet = tweetMapper.toTweet(tweetRequest, tweet);

        return tweetMapper.toTweetResponse(tweetService.update(updatedTweet));
    }

    public void delete(Long id) {
        tweetService.deleteById(id);
    }
}
