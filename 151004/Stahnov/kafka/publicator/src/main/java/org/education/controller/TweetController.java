package org.education.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.education.bean.DTO.TweetRequestTo;
import org.education.bean.DTO.TweetResponseTo;
import org.education.bean.Tweet;
import org.education.exception.AlreadyExists;
import org.education.exception.ErrorResponse;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchTweet;
import org.education.service.TweetService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tweets")
@Tag(name = "Твиты")
public class TweetController {
    private final TweetService tweetService;

    private final ModelMapper modelMapper;

    public TweetController(TweetService tweetService, ModelMapper modelMapper) {
        this.tweetService = tweetService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Получение всех твитов")
    @GetMapping
    public ResponseEntity<List<TweetResponseTo>> getAllTweets(){
        return new ResponseEntity<>(tweetService.getAll(), HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Создание твита")
    public ResponseEntity<TweetResponseTo> createTweet(@RequestBody @Valid TweetRequestTo tweetRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(tweetService.create(modelMapper.map(tweetRequestTo, Tweet.class), tweetRequestTo.getCreatorId()), HttpStatus.valueOf(201));
    }


    @PutMapping
    @Operation(summary = "Обновление твита")
    public ResponseEntity<TweetResponseTo> updateTweet(@RequestBody @Valid TweetRequestTo tweetRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(tweetService.update(modelMapper.map(tweetRequestTo, Tweet.class)), HttpStatus.valueOf(200));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удаления твита")
    public ResponseEntity<HttpStatus> deleteTweet(@PathVariable int id){
        tweetService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получение твита по id")
    public ResponseEntity<TweetResponseTo> getTweet(@PathVariable int id){
        return new ResponseEntity<>(tweetService.getById(id), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchTweet exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IncorrectValuesException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(AlreadyExists exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 403),HttpStatus.valueOf(403));
    }
}
