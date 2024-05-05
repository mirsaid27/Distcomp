package com.example.distributedcomputing.controller;


import com.example.distributedcomputing.model.request.TweetRequestTo;
import com.example.distributedcomputing.model.response.TweetResponseTo;
import com.example.distributedcomputing.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/tweets")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseTo create(@RequestBody TweetRequestTo tweetRequestTo) {
        return tweetService.save(tweetRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tweetService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<TweetResponseTo> getAll() {
        return tweetService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public TweetResponseTo getById(@PathVariable Long id) {
        return tweetService.getById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public TweetResponseTo update(@RequestBody TweetRequestTo tweetRequestTo) {
        return tweetService.update(tweetRequestTo);
    }
}
