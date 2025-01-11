package org.example.tweetapi.service;

import lombok.AllArgsConstructor;
import org.example.tweetapi.mapper.TweetMapper;
import org.example.tweetapi.model.dto.request.TweetRequestTo;
import org.example.tweetapi.model.dto.response.TweetResponseTo;
import org.example.tweetapi.model.entity.Author;
import org.example.tweetapi.model.entity.Tweet;
import org.example.tweetapi.repository.AuthorRepository;
import org.example.tweetapi.repository.TweetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private AuthorRepository authorRepository;

    public TweetResponseTo createTweet(TweetRequestTo tweetRequestDto) {
        if (!authorRepository.existsById(tweetRequestDto.getAuthorId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author ID is invalid");
        }
        if (tweetRepository.existsByTitle(tweetRequestDto.getTitle())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Title already exists");
        }
        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);

        Author author = new Author();
        author.setId(tweetRequestDto.getAuthorId());
        tweet.setAuthor(author);

        tweet.setContent(tweetRequestDto.getContent());

        tweet = tweetRepository.save(tweet);

        return tweetMapper.toResponse(tweet);
    }

    public TweetResponseTo getTweetById(Long id) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        return tweetOptional.map(tweetMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
    }

    public List<TweetResponseTo> getAllStories() {
        return tweetRepository.findAll().stream()
                .map(tweetMapper::toResponse)
                .toList();
    }

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

    public void deleteTweet(Long id) {
        if (!tweetRepository.existsById(id)) {
            throw new RuntimeException("Tweet not found");
        }
        tweetRepository.deleteById(id);
    }
}
