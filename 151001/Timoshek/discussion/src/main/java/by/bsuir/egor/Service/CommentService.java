package by.bsuir.egor.Service;


import by.bsuir.egor.Entity.Comment;
import by.bsuir.egor.Entity.Issue;
import by.bsuir.egor.dto.CommentMapper;
import by.bsuir.egor.dto.CommentRequestTo;
import by.bsuir.egor.dto.CommentResponseTo;
import by.bsuir.egor.repositories.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private WebClient issueRepo = WebClient.create();
    public List<CommentResponseTo> getAll() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseTo> resultList = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            resultList.add(CommentMapper.INSTANCE.postToPostResponseTo(commentList.get(i)));
        }
        return resultList;
    }
    public List<CommentRequestTo> getAllKafka() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentRequestTo> resultList = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            resultList.add(CommentMapper.INSTANCE.postToPostRequestTo(commentList.get(i)));
        }
        return resultList;
    }

    public CommentResponseTo update(CommentRequestTo updatingcomment) {
        Comment comment =  CommentMapper.INSTANCE.postRequestToToPost(updatingcomment);
        if (validateComment(comment)) {
            CommentResponseTo responseTo;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            try {
                Optional<Issue> relatedIssue = issueRepo.get()
                        .uri("http://localhost:24110/api/v1.0/issues/" + comment.getIssueId())
                        .headers(httpHeaders -> httpHeaders.addAll(headers))
                        .retrieve()
                        .bodyToMono(Issue.class)
                        .blockOptional();
                if (relatedIssue.isPresent()) {
                    return CommentMapper.INSTANCE.postToPostResponseTo(commentRepository.save(comment));
                } else {
                    return new CommentResponseTo();
                }
            } catch (WebClientResponseException e) {
                return new CommentResponseTo();
            }
        }return new CommentResponseTo();
    }
    public CommentRequestTo updateKafka(CommentRequestTo updatingcomment) {
        return CommentMapper.INSTANCE.postToPostRequestTo(commentRepository.save(CommentMapper.INSTANCE.postRequestToToPost(updatingcomment)));
    }

    public CommentResponseTo getById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            return CommentMapper.INSTANCE.postToPostResponseTo(comment.get());
        } else {
            return new CommentResponseTo();
        }
    }

    public CommentRequestTo getByIdKafka(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            return CommentMapper.INSTANCE.postToPostRequestTo(comment.get());
        } else {
            return new CommentRequestTo();
        }
    }


    public boolean deleteById(long id) {

        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            commentRepository.delete(comment.get());
            return  true;
        }
        return  false;
    }
    public CommentRequestTo deleteByIdKafka(long id) {

        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            commentRepository.delete(comment.get());
            return  CommentMapper.INSTANCE.postToPostRequestTo(comment.get());
        }
        return  CommentMapper.INSTANCE.postToPostRequestTo(null);
    }

    public ResponseEntity<CommentResponseTo> add(CommentRequestTo commentRequestTo) {
        commentRequestTo.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()) % 1000000);
        Comment comment =  CommentMapper.INSTANCE.postRequestToToPost(commentRequestTo);
        if (validateComment(comment)) {
            CommentResponseTo responseTo;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            try {
                Optional<Issue> relatedIssue = issueRepo.get()
                        .uri("http://localhost:24110/api/v1.0/issues/" + comment.getIssueId())
                        .headers(httpHeaders -> httpHeaders.addAll(headers))
                        .retrieve()
                        .bodyToMono(Issue.class)
                        .blockOptional();
                if (relatedIssue.isPresent()) {
                    Comment currComment = commentRepository.save(comment);
                    responseTo = CommentMapper.INSTANCE.postToPostResponseTo(currComment);
                    return new ResponseEntity<>(responseTo,HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(CommentMapper.INSTANCE.postToPostResponseTo(comment), HttpStatus.FORBIDDEN);
                }
            } catch (WebClientResponseException e) {
                return new ResponseEntity<>(CommentMapper.INSTANCE.postToPostResponseTo(comment), HttpStatus.FORBIDDEN);
            }
        }return  new ResponseEntity<>(CommentMapper.INSTANCE.postToPostResponseTo(comment), HttpStatus.FORBIDDEN);
    }

    public CommentRequestTo addKafka(CommentRequestTo commentRequestTo) {
        commentRequestTo.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()) % 1000000);
        Comment comment =  CommentMapper.INSTANCE.postRequestToToPost(commentRequestTo);

        return  CommentMapper.INSTANCE.postToPostRequestTo(commentRepository.save(comment));
    }

    private boolean validateComment(Comment comment) {
        if( comment.getContent()!=null) {
            String content = comment.getContent();
            if (content.length() >= 2 && content.length() <= 2048) return true;
        }
        return false;
    }
}
