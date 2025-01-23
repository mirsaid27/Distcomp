package ru.bsuir.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.request.TweetRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.dto.response.TweetResponseTo;
import ru.bsuir.services.TweetService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<TweetResponseTo> create(@RequestBody @Valid TweetRequestTo dto) {
        TweetResponseTo response = tweetService.createTweet(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TweetResponseTo>> getAll() {
        return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseTo> getById(@PathVariable Long id) {
        return tweetService.getTweetById(id)
                .map(tweet -> new ResponseEntity<>(tweet, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<TweetResponseTo> update(@RequestBody @Valid TweetRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return tweetService.updateTweet(dto.id(), dto)
                .map(updatedTweet -> new ResponseEntity<>(updatedTweet, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid TweetRequestTo dto) {
        return tweetService.updateTweet(id, dto)
                .map(updatedTweet -> new ResponseEntity<>(updatedTweet, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (tweetService.deleteTweet(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
