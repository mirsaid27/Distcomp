package by.bsuir.egor.Service;


import by.bsuir.egor.Entity.Comment;
import by.bsuir.egor.Entity.Issue;

import by.bsuir.egor.dto.*;
import by.bsuir.egor.kafka.KafkaMessaging;
import by.bsuir.egor.kafka.KafkaMethods;
import by.bsuir.egor.kafka.KafkaRequest;
import by.bsuir.egor.redis.RedisPostRepository;
import by.bsuir.egor.repositories.IssueRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommentService implements IService<CommentResponseTo, CommentRequestTo> {
    private WebClient commentRepository = WebClient.create();
    @Autowired
    private KafkaMessaging sender;
    @Autowired
    private IssueRepository  issueRepository;
    @Autowired
    private RedisPostRepository redisRepository;


    public List<CommentResponseTo> getAll() {
        List<CommentResponseTo> resultList;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            List<Comment> comments = commentRepository.get()
                    .uri("http://localhost:24130/api/v1.0/comments")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToFlux(Comment.class)
                    .collectList()
                    .block();
            resultList  = new ArrayList<>();
            for (Comment comment :
                    comments) {
                resultList.add(CommentMapper.INSTANCE.commentToCommentResponseTo(comment));
            }
        }catch (WebClientResponseException e) {
            return new ArrayList<>();
        }
        return resultList;
    }
    public List<CommentResponseTo> getAllKafka() {
        List<Comment> commentList = redisRepository.getAll();;
        List<CommentResponseTo> resultList = new ArrayList<>();
        if(!commentList.isEmpty())
        {
            for (int i = 0; i < commentList.size(); i++) {
                resultList.add(CommentMapper.INSTANCE.commentToCommentResponseTo(commentList.get(i)));
            }
            return  resultList;
        }
        else {
            List<CommentRequestTo> result = new ArrayList<>();
            KafkaRequest request = new KafkaRequest();
            request.setKey(UUID.randomUUID());
            request.setRequestMethod(KafkaMethods.READ_ALL);
            try {
                result = sender.sendMessage(request);
                if (result.isEmpty()) {
                    return null;
                } else {
                    for(int i = 0; i<result.size();i++)
                    {
                        redisRepository.add(CommentMapper.INSTANCE.commentRequestToToComment(result.get(i)));
                    }
                    return CommentMapper.INSTANCE.listRequestToResponse(result);
                }
            } catch (JsonProcessingException e) {
                return null;
            }
        }

    }

    public CommentResponseTo update(CommentRequestTo updatingComment) {
        Comment comment = CommentMapper.INSTANCE.commentRequestToToComment(updatingComment);
        if (validatePost(comment)) {
            Optional<Issue> relatedIssue = issueRepository.findById(comment.getIssueId());
            if (relatedIssue.isPresent()) {
                Optional<Comment> redisComment = redisRepository.getById(comment.getId());
                if (redisComment.isPresent() && comment.equals(redisComment.get())) {
                    return CommentMapper.INSTANCE.commentToCommentResponseTo(redisComment.get());
                } else {
                    List<CommentRequestTo> result;
                    KafkaRequest request = new KafkaRequest();
                    request.setKey(UUID.randomUUID());
                    request.getDtoToTransfer().add(updatingComment);
                    request.setRequestMethod(KafkaMethods.UPDATE);
                    try {
                        result = sender.sendMessage(request);
                        if (!result.isEmpty()) {
                            Comment resultComment = CommentMapper.INSTANCE.commentRequestToToComment(result.get(0));
                            redisRepository.update(resultComment);
                            return CommentMapper.INSTANCE.commentToCommentResponseTo(CommentMapper.INSTANCE.commentRequestToToComment(result.get(0)));
                        }
                        return new CommentResponseTo();
                    } catch (JsonProcessingException e) {
                        return new CommentResponseTo();
                    }
                }
            }
            else{
                return new CommentResponseTo();
            }

        }
        else  return new CommentResponseTo();
    }

    public CommentResponseTo getById(long id) {
        Optional<Comment> redisComment = redisRepository.getById(id);
        if (redisComment.isPresent()) {
            return CommentMapper.INSTANCE.commentToCommentResponseTo(redisComment.get());
        } else{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            try {
                Optional<Comment> comment = commentRepository.get()
                        .uri("http://localhost:24130/api/v1.0/comments/" + id)
                        .headers(httpHeaders -> httpHeaders.addAll(headers))
                        .retrieve()
                        .bodyToMono(Comment.class)
                        .blockOptional();
                if (comment.isPresent()) {
                    redisRepository.add(comment.get());
                    return CommentMapper.INSTANCE.commentToCommentResponseTo(comment.get());
                } else {
                    return CommentMapper.INSTANCE.commentToCommentResponseTo(new Comment());
                }
            } catch (WebClientResponseException e) {
                return CommentMapper.INSTANCE.commentToCommentResponseTo(new Comment());
            }
        }
    }

    public CommentResponseTo getByIdKafka(long id) {
        Optional<Comment> redisComment = redisRepository.getById(id);
        if (redisComment.isPresent()) {
            return CommentMapper.INSTANCE.commentToCommentResponseTo(redisComment.get());
        }else {
            CommentRequestTo commentRequestTo = new CommentRequestTo();
            commentRequestTo.setId(id);
            KafkaRequest request = new KafkaRequest();
            request.setKey(UUID.randomUUID());
            request.getDtoToTransfer().add(commentRequestTo);
            request.setRequestMethod(KafkaMethods.READ);
            try {
                List<CommentRequestTo> result = sender.sendMessage(request);
                if (!result.isEmpty()) {
                    redisRepository.add(CommentMapper.INSTANCE.commentRequestToToComment(result.get(0)));
                    return CommentMapper.INSTANCE.commentToCommentResponseTo(CommentMapper.INSTANCE.commentRequestToToComment(result.get(0)));
                }
                ;
            } catch (JsonProcessingException e) {
                return null;
            }
            return null;
        }
    }

    public boolean deleteById(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            Optional<Comment> comment = commentRepository.get()
                    .uri("http://localhost:24130/api/v1.0/comments/" + id)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(Comment.class)
                    .blockOptional();
            if (comment.isPresent()) {
                headers.setContentType(MediaType.APPLICATION_JSON);
                commentRepository.delete()
                        .uri("http://localhost:24130/api/v1.0/comments/" + id)
                        .headers(httpHeaders -> httpHeaders.addAll(headers))
                        .retrieve()
                        .bodyToMono(Comment.class)
                        .block();
                return true;
            }else return false;
        }catch (WebClientResponseException e){
            return false;
        }
    }

    public boolean deleteByIdKafka(long id)
    {
        CommentRequestTo commentRequestTo = new CommentRequestTo();
        commentRequestTo.setId(id);
        KafkaRequest request= new KafkaRequest();
        request.setKey(UUID.randomUUID());
        request.getDtoToTransfer().add(commentRequestTo);
        request.setRequestMethod(KafkaMethods.DELETE);
        try {
            List<CommentRequestTo> result = sender.sendMessage(request);
            if(!result.isEmpty())
            {
                if(result.get(0) == null)
                {
                    return false;
                }
                else
                {
                    return  true;
                }
            }
        }catch (JsonProcessingException e){
            return false;
        }
        redisRepository.delete(id);
        return true;
    }
    public ResponseEntity<CommentResponseTo> add(CommentRequestTo commentRequestTo) {
        Comment comment = CommentMapper.INSTANCE.commentRequestToToComment(commentRequestTo);
        if (validatePost(comment)) {
            Optional<Issue> relatedIssue = issueRepository.findById(comment.getIssueId());
            if (relatedIssue.isPresent()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                try {
                    Optional<Comment> savedComment = commentRepository.post()
                            .uri("http://localhost:24130/api/v1.0/comments")
                            .headers(httpHeaders -> httpHeaders.addAll(headers))
                            .bodyValue(comment)
                            .retrieve()
                            .bodyToMono(Comment.class)
                            .blockOptional();
                    return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(savedComment.get()), HttpStatus.CREATED);
                } catch (WebClientResponseException e) {
                    return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
            }
        }
        else return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<CommentResponseTo> addKafka(CommentRequestTo commentRequestTo) {
        Comment comment = CommentMapper.INSTANCE.commentRequestToToComment(commentRequestTo);
        if (validatePost(comment)) {
            Optional<Issue> relatedIssue = issueRepository.findById(comment.getIssueId());
            if (relatedIssue.isPresent()) {
                List<CommentRequestTo> result;
                KafkaRequest request = new KafkaRequest();
                request.setKey(UUID.randomUUID());
                request.getDtoToTransfer().add(commentRequestTo);
                request.setRequestMethod(KafkaMethods.CREATE);
                try {
                    result = sender.sendMessage(request);
                    if(!result.isEmpty())
                    {
                        redisRepository.add(CommentMapper.INSTANCE.commentRequestToToComment(result.get(0)));
                        return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(CommentMapper.INSTANCE.commentRequestToToComment(result.get(0))),HttpStatus.CREATED);
                    }
                    return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
                } catch (JsonProcessingException e) {
                    return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
            }
        }
        else  return new ResponseEntity<>(CommentMapper.INSTANCE.commentToCommentResponseTo(comment), HttpStatus.FORBIDDEN);
    }

    private boolean validatePost(Comment comment) {
        if( comment.getContent()!=null) {
            String content = comment.getContent();
            if (content.length() >= 2 && content.length() <= 2048) return true;
        }
        return false;
    }
}
