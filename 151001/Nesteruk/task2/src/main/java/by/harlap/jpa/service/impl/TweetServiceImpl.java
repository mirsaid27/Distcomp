package by.harlap.jpa.service.impl;

import by.harlap.jpa.exception.EntityNotFoundException;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.repository.impl.TweetRepository;
import by.harlap.jpa.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TweetServiceImpl implements TweetService {

    public static final String TWEET_NOT_FOUND_MESSAGE = "Tweet with id '%d' doesn't exist";
    private final TweetRepository tweetRepository;

    @Override
    public Tweet findById(Long id) {
        return tweetRepository.findById(id).orElseThrow(() -> {
            final String message = TWEET_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        tweetRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(TWEET_NOT_FOUND_MESSAGE));

        tweetRepository.deleteById(id);
    }

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet update(Tweet tweet) {
        tweetRepository.findById(tweet.getId()).orElseThrow(()-> new EntityNotFoundException(TWEET_NOT_FOUND_MESSAGE));

        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }
}
