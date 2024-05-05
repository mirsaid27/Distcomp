package by.bsuir.services;

import by.bsuir.dao.TweetDao;
import by.bsuir.dto.TweetRequestTo;
import by.bsuir.dto.TweetResponseTo;
import by.bsuir.entities.Tweet;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.TweetListMapper;
import by.bsuir.mapper.TweetMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;
    @Autowired
    TweetDao tweetDao;
    @Autowired
    TweetListMapper tweetListMapper;

    public TweetResponseTo getTweetById(@Min(0) Long id) throws NotFoundException{
        Optional<Tweet> tweet = tweetDao.findById(id);
        return tweet.map(value -> tweetMapper.tweetToTweetResponse(value)).orElseThrow(() -> new NotFoundException("Tweet not found!", 40004L));
    }

    public List<TweetResponseTo> getTweets() {
        return tweetListMapper.toTweetResponseList(tweetDao.findAll());
    }

    public TweetResponseTo saveTweet(@Valid TweetRequestTo tweet) {
        Tweet tweetToSave = tweetMapper.tweetRequestToTweet(tweet);
        return tweetMapper.tweetToTweetResponse(tweetDao.save(tweetToSave));
    }

    public void deleteTweet(@Min(0) Long id) throws DeleteException {
        tweetDao.delete(id);
    }

    public TweetResponseTo updateTweet(@Valid TweetRequestTo tweet) throws UpdateException {
        Tweet tweetToUpdate = tweetMapper.tweetRequestToTweet(tweet);
        return tweetMapper.tweetToTweetResponse(tweetDao.update(tweetToUpdate));
    }
}
