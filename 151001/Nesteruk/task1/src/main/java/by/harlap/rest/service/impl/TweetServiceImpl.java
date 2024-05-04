package by.harlap.rest.service.impl;

import by.harlap.rest.exception.EntityNotFoundException;
import by.harlap.rest.model.Tweet;
import by.harlap.rest.repository.AbstractRepository;
import by.harlap.rest.service.EditorService;
import by.harlap.rest.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TweetServiceImpl implements TweetService {

    public static final String TWEET_NOT_FOUND_MESSAGE = "Tweet with id '%d' doesn't exist";
    private final AbstractRepository <Tweet, Long> tweetRepository;
    private final EditorService authorService;

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
        authorService.findById(tweet.getEditorId());
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet update(Tweet tweet) {
        tweetRepository.findById(tweet.getId()).orElseThrow(()-> new EntityNotFoundException(TWEET_NOT_FOUND_MESSAGE));

        authorService.findById(tweet.getEditorId());
        return tweetRepository.update(tweet);
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }
}
