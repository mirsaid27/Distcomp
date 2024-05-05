package com.example.publicator.controller;

import com.example.publicator.dto.TweetRequestTo;
import com.example.publicator.dto.TweetResponseTo;
import com.example.publicator.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class TweetController {
    @Autowired
    TweetService tweetService;

    @GetMapping(value = "tweets", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> readAll(
            @RequestParam (required = false, defaultValue = "0") Integer pageInd,
            @RequestParam (required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam (required = false, defaultValue = "id") String sortedBy,
            @RequestParam (required = false, defaultValue = "desc") String direction)
    {
        List<TweetResponseTo> list = tweetService.readAll(pageInd, numOfElem, sortedBy, direction);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "tweets/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        TweetResponseTo tweet = tweetService.read(id);
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }

    @PostMapping(value = "tweets")
    public ResponseEntity<?> create(@RequestBody TweetRequestTo tweetRequestTo) {
        TweetResponseTo tweetResponseTo = tweetService.create(tweetRequestTo);
        return new ResponseEntity<>(tweetResponseTo, HttpStatus.CREATED);
    }

    @PutMapping(value = "tweets")
    public ResponseEntity<?> update(@RequestBody TweetRequestTo tweetRequestTo) {
        TweetResponseTo tweetResponseTo = tweetService.update(tweetRequestTo, tweetRequestTo.getId());
        return new ResponseEntity<>(tweetResponseTo, HttpStatus.OK);
    }

    @DeleteMapping(value = "tweets/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean res = tweetService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
