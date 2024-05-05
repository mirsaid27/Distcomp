package by.harlap.jpa.controller;

import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.facade.TweetFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

    private final TweetFacade tweetFacade;

    @GetMapping("/{id}")
    public TweetResponseDto findTweetById(@PathVariable("id") Long id) {
        return tweetFacade.findById(id);
    }

    @GetMapping
    public List<TweetResponseDto> findAll() {
        return tweetFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TweetResponseDto saveTweet(@RequestBody @Valid CreateTweetDto tweetRequest) {
        return tweetFacade.save(tweetRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable("id") Long id) {
        tweetFacade.delete(id);
    }

    @PutMapping
    public TweetResponseDto updateTweet(@RequestBody @Valid UpdateTweetDto tweetRequest) {
        return tweetFacade.update(tweetRequest);
    }
}
