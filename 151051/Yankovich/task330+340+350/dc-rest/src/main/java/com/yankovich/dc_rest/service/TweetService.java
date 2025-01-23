package com.yankovich.dc_rest.service;

import com.yankovich.dc_rest.mapper.TweetMapper;
import com.yankovich.dc_rest.model.Author;
import com.yankovich.dc_rest.model.Tweet;
import com.yankovich.dc_rest.model.dto.requests.TweetRequestTo;
import com.yankovich.dc_rest.model.dto.responses.TweetResponseTo;
import com.yankovich.dc_rest.repository.interfaces.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    @CacheEvict(value = "tweets", allEntries = true)
    public TweetResponseTo createTweet(TweetRequestTo tweetRequestDto) {
        if (tweetRepository.existsByTitle(tweetRequestDto.getTitle())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "{error: Duplicate tweet title}");
        }
        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);

        Author author = new Author();
        author.setId(tweetRequestDto.getAuthorId());
        tweet.setAuthor(author);

        tweet = tweetRepository.save(tweet);

        return tweetMapper.toResponse(tweet);
    }

    @Cacheable(value = "tweets", key = "#id")
    public TweetResponseTo getTweetById(Long id) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        return tweetOptional.map(tweetMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
    }

    //@Cacheable(value = "tweetsList")
    public List<TweetResponseTo> getAllTweets() {
        return tweetRepository.findAll().stream()
                .map(tweetMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "tweets", key = "#id")
    public TweetResponseTo updateTweet(Long id, TweetRequestTo tweetRequestDto) {
        Tweet existingTweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found"));

        if (tweetRequestDto.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        existingTweet.setTitle(tweetRequestDto.getTitle());
        existingTweet.setContent(tweetRequestDto.getContent());

        tweetRepository.save(existingTweet);
        return tweetMapper.toResponse(existingTweet);
    }

    @CacheEvict(value = "tweets", key = "#id")
    public void deleteTweet(Long id) {
        if (!tweetRepository.existsById(id)) {
            throw new RuntimeException("Tweet not found");
        }
        tweetRepository.deleteById(id);
    }
}
