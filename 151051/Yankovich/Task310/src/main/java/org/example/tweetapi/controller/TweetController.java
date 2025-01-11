package org.example.tweetapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tweetapi.model.dto.request.TweetRequestTo;
import org.example.tweetapi.model.dto.response.TweetResponseTo;
import org.example.tweetapi.service.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    // Создать новую историю
    @PostMapping
    public ResponseEntity<TweetResponseTo> createTweet(@RequestBody @Valid TweetRequestTo tweetRequestDto) {
        TweetResponseTo tweetResponseDto = tweetService.createTweet(tweetRequestDto);
        return new ResponseEntity<>(tweetResponseDto, HttpStatus.CREATED);
    }

    // Получить историю по id
    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseTo> getTweetById(@PathVariable Long id) {
        TweetResponseTo tweetResponseDto = tweetService.getTweetById(id);
        return new ResponseEntity<>(tweetResponseDto, HttpStatus.OK);
    }

    // Получить все истории
    @GetMapping
    public ResponseEntity<List<TweetResponseTo>> getAllStories() {
        List<TweetResponseTo> tweetResponseDtos = tweetService.getAllStories();
        return new ResponseEntity<>(tweetResponseDtos, HttpStatus.OK);
    }

    // Обновить историю по id
    @PutMapping("/{id}")
    public ResponseEntity<TweetResponseTo> updateTweet(
            @Valid
            @PathVariable Long id,
            @RequestBody TweetRequestTo tweetRequestDto) {
        TweetResponseTo updatedTweetResponseDto = tweetService.updateTweet(id, tweetRequestDto);
        return new ResponseEntity<>(updatedTweetResponseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TweetResponseTo> updateTweet(@RequestBody @Valid TweetRequestTo tweetRequestDto) {
        if (tweetRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Используем ID из тела запроса для обновления
        TweetResponseTo updatedTweetResponseDto = tweetService.updateTweet(tweetRequestDto.getId(), tweetRequestDto);
        return new ResponseEntity<>(updatedTweetResponseDto, HttpStatus.OK);
    }

    // Удалить историю по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        try {
            tweetService.deleteTweet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
