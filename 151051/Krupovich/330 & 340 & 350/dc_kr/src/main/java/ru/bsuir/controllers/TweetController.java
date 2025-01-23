package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

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
        TweetResponseTo response = tweetService.getTweetById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TweetResponseTo> update(@RequestBody @Valid TweetRequestTo dto) {
        if (dto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TweetResponseTo response = tweetService.updateTweet(dto.getId(), dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponseTo> updateById( @Valid @PathVariable Long id, @RequestBody @Valid TweetRequestTo dto) {
        TweetResponseTo response = tweetService.updateTweet(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tweetService.deleteTweet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
