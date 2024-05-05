package by.bsuir.controllers;

import by.bsuir.dto.TweetRequestTo;
import by.bsuir.dto.TweetResponseTo;
import by.bsuir.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {
    @Autowired
    TweetService tweetService;

    @GetMapping
    public ResponseEntity<List<TweetResponseTo>> getTweets() {
        return ResponseEntity.status(200).body(tweetService.getTweets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseTo> getTweet(@PathVariable Long id) {
        return ResponseEntity.status(200).body(tweetService.getTweetById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TweetResponseTo> saveTweet(@RequestBody TweetRequestTo tweet) {
        TweetResponseTo savedTweet = tweetService.saveTweet(tweet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTweet);
    }

    @PutMapping()
    public ResponseEntity<TweetResponseTo> updateTweet(@RequestBody TweetRequestTo tweet) {
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.updateTweet(tweet));
    }
}