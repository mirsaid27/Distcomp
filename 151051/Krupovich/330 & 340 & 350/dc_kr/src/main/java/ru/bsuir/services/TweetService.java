package ru.bsuir.services;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.TweetRequestTo;
import ru.bsuir.dto.response.TweetResponseTo;
import ru.bsuir.entity.Editor;
import ru.bsuir.entity.Tweet;
import ru.bsuir.irepositories.ITweetRepository;
import ru.bsuir.mapper.TweetMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@EnableCaching
public class TweetService {

    private final ITweetRepository tweetRepository;

    private final TweetMapper tweetMapper;

    @CacheEvict(value = "tweets", allEntries = true)
    public TweetResponseTo createTweet(TweetRequestTo tweetRequest) {

        Tweet tweet = tweetMapper.toEntity(tweetRequest);

        Editor editor = new Editor();
        editor.setId(tweetRequest.getEditorId());
        tweet.setEditor(editor);

        tweet = tweetRepository.save(tweet);

        return tweetMapper.toDTO(tweet);
    }
    @Cacheable(value = "tweets", key = "#id")
    public TweetResponseTo getTweetById(Long id) {
        Optional<Tweet> tweetOpt = tweetRepository.findById(id);
        return tweetOpt.map(tweetMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
    }

    public List<TweetResponseTo> getAllTweets(){
        return tweetRepository.findAll().stream()
                .map(tweetMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = "tweets", key = "#id")
    public TweetResponseTo updateTweet(Long id, TweetRequestTo tweetRequest) {

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found"));

        if(tweetRequest.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        tweet.setTitle(tweetRequest.getTitle());
        tweet.setContent(tweetRequest.getContent());
        tweetRepository.save(tweet);
        return tweetMapper.toDTO(tweet);
    }

    @CacheEvict(value = "tweets", key = "#id")
    public void deleteTweet(Long id) {

        if(!tweetRepository.existsById(id)) {
            throw new RuntimeException("Tweet not found");

        }
        tweetRepository.deleteById(id);
    }
}
