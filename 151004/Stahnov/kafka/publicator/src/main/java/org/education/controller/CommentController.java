package org.education.controller;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.education.bean.Comment;
import org.education.bean.DTO.CommentRequestTo;
import org.education.bean.DTO.CommentResponseTo;
import org.education.exception.AlreadyExists;
import org.education.exception.ErrorResponse;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchComment;
import org.education.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
@Tag(name = "Комменты")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Получение всех комментов")
    @GetMapping
    public ResponseEntity<List<CommentResponseTo>> getAllComments(){
        List<CommentResponseTo> response = new ArrayList<>();
        for(Comment comment : commentService.getAll()){
            response.add(modelMapper.map(comment, CommentResponseTo.class));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Создание коммента")
    @PostMapping
    public ResponseEntity<CommentResponseTo> createComment(@RequestBody @Valid CommentRequestTo commentRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Comment comment = commentService.create(commentRequestTo);
        return new ResponseEntity<>(modelMapper.map(comment, CommentResponseTo.class), HttpStatus.valueOf(201));
    }

    @Operation(summary = "Обновление коммента")
    @PutMapping
    public ResponseEntity<CommentResponseTo> updateComment(@RequestBody @Valid CommentRequestTo commentRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        Comment comment = commentService.update(commentRequestTo);
        return new ResponseEntity<>(modelMapper.map(comment, CommentResponseTo.class), HttpStatus.valueOf(200));
    }

    @Operation(summary = "Удаления коммента")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable int id){
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @Operation(summary = "Получение коммента по id")
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseTo> getComment(@PathVariable int id){
        return new ResponseEntity<>(modelMapper.map(commentService.getById(id), CommentResponseTo.class), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchComment exception){
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

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(HttpClientErrorException.NotFound notFound){
        return new ResponseEntity<>(new ErrorResponse(notFound.getMessage(), 404),HttpStatus.valueOf(404));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(HttpClientErrorException.BadRequest badRequest){
        return new ResponseEntity<>(new ErrorResponse(badRequest.getMessage(), 400),HttpStatus.valueOf(400));
    }
}
