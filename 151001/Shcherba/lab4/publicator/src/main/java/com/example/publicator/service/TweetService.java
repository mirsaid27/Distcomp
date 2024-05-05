package com.example.publicator.service;

import com.example.publicator.dto.TweetRequestTo;
import com.example.publicator.dto.TweetResponseTo;
import com.example.publicator.exception.DuplicateException;
import com.example.publicator.exception.NotFoundException;
import com.example.publicator.mapper.TweetListMapper;
import com.example.publicator.mapper.TweetMapper;
import com.example.publicator.model.Tweet;
import com.example.publicator.repository.UserRepository;
import com.example.publicator.repository.TweetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;
    @Autowired
    TweetListMapper tweetListMapper;
    @Autowired
    TweetRepository tweetRepository;
    //TweetDao tweetDao;
    @Autowired
    UserRepository userRepository;

    public TweetResponseTo create(@Valid TweetRequestTo tweetRequestTo){
        Tweet tweet = tweetMapper.tweetRequestToTweet(tweetRequestTo);
        if(tweetRepository.existsByTitle(tweet.getTitle())){
            throw new DuplicateException("Title duplication", 403);
        }
        if(tweetRequestTo.getUserId() != 0){
            tweet.setUser(userRepository.findById(tweetRequestTo.getUserId()).orElseThrow(() -> new NotFoundException("User not found", 404)));
        }
        return tweetMapper.tweetToTweetResponse(tweetRepository.save(tweet));
    }

    public List<TweetResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction) {
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        Page<Tweet> res = tweetRepository.findAll(p);
        return tweetListMapper.toTweetResponseList(res.toList());
    }

    public TweetResponseTo read(@Min(0) int id) throws NotFoundException {
        if(tweetRepository.existsById(id)){
            TweetResponseTo tweet = tweetMapper.tweetToTweetResponse(tweetRepository.getReferenceById(id));
            return tweet;
        }
        else
            throw new NotFoundException("Tweet not found", 404);
    }

    public TweetResponseTo update(@Valid TweetRequestTo tweetRequestTo, @Min(0) int id) throws NotFoundException {
        if(tweetRepository.existsById(id)){
            Tweet tweet = tweetMapper.tweetRequestToTweet(tweetRequestTo);
            tweet.setId(id);
            if(tweetRequestTo.getUserId() != 0){
                tweet.setUser(userRepository.findById(tweetRequestTo.getUserId()).orElseThrow(() -> new NotFoundException("User not found", 404)));
            }
            return tweetMapper.tweetToTweetResponse(tweetRepository.save(tweet));
        }
        else
            throw new NotFoundException("Tweet not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(tweetRepository.existsById(id)){
            tweetRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Tweet not found", 404);
    }

}
