package by.ryabchikov.tweet_service.service.impl;

import by.ryabchikov.tweet_service.dto.tweet.TweetRequestTo;
import by.ryabchikov.tweet_service.dto.tweet.TweetResponseTo;
import by.ryabchikov.tweet_service.dto.tweet.TweetUpdateRequestTo;
import by.ryabchikov.tweet_service.entity.Creator;
import by.ryabchikov.tweet_service.entity.Tweet;
import by.ryabchikov.tweet_service.exception.CreatorNotFoundException;
import by.ryabchikov.tweet_service.exception.TweetNotFoundException;
import by.ryabchikov.tweet_service.mapper.TweetMapper;
import by.ryabchikov.tweet_service.repository.CreatorRepository;
import by.ryabchikov.tweet_service.repository.TweetRepository;
import by.ryabchikov.tweet_service.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final CreatorRepository creatorRepository;
    private final TweetMapper tweetMapper;

    @Override
    public TweetResponseTo create(TweetRequestTo tweetRequestTo) {
        return tweetMapper.toTweetResponseTo(
                tweetRepository.save(tweetMapper.toTweet(tweetRequestTo))
        );
    }

    @Override
    public List<TweetResponseTo> readAll() {
        return tweetRepository.findAll().stream()
                .map(tweetMapper::toTweetResponseTo)
                .toList();
    }

    @Override
    public TweetResponseTo readById(Long id) {
        return tweetMapper.toTweetResponseTo(
                tweetRepository.findById(id).orElseThrow(() -> TweetNotFoundException.byId(id))
        );
    }

    @Override
    public TweetResponseTo update(TweetUpdateRequestTo tweetUpdateRequestTo) {
        long tweetId = tweetUpdateRequestTo.id();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> TweetNotFoundException.byId(tweetId));

        long creatorId = tweetUpdateRequestTo.creatorId();
        Creator creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> CreatorNotFoundException.byId(creatorId));

        tweet.setTitle(tweetUpdateRequestTo.title());
        tweet.setContent(tweetUpdateRequestTo.content());
        tweet.setCreator(creator);

        return tweetMapper.toTweetResponseTo(tweet);
    }

    @Override
    public void deleteById(Long id) {
        tweetRepository.findById(id)
                .orElseThrow(() -> TweetNotFoundException.byId(id));

        tweetRepository.deleteById(id);
    }
}
