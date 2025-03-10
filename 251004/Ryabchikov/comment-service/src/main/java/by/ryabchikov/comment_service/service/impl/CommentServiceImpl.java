package by.ryabchikov.comment_service.service.impl;

import by.ryabchikov.comment_service.dto.CommentRequestTo;
import by.ryabchikov.comment_service.dto.CommentResponseTo;
import by.ryabchikov.comment_service.dto.CommentUpdateRequestTo;
import by.ryabchikov.comment_service.entity.Comment;
import by.ryabchikov.comment_service.exception.CommentNotFoundException;
import by.ryabchikov.comment_service.mapper.CommentMapper;
import by.ryabchikov.comment_service.repository.CommentRepository;
import by.ryabchikov.comment_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    //private final TweetRepository tweetRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponseTo create(CommentRequestTo commentRequestTo) {
        return commentMapper.toCommentResponseTo(
                commentRepository.save(commentMapper.toComment(commentRequestTo))
        );
    }

    @Override
    @Transactional
    public List<CommentResponseTo> readAll() {
        ArrayList<CommentResponseTo> commentResponseTos = new ArrayList<>();
        commentRepository.findAll().forEach(comment -> {
            CommentResponseTo commentResponseTo = commentMapper.toCommentResponseTo(comment);
            commentResponseTos.add(commentResponseTo);
        });
        return commentResponseTos;
    }

    @Override
    @Transactional
    public CommentResponseTo readById(Long id) {
        return commentMapper.toCommentResponseTo(
                commentRepository.findById(id).orElseThrow(() -> CommentNotFoundException.byId(id))
        );
    }

    @Override
    @Transactional
    public CommentResponseTo update(CommentUpdateRequestTo commentUpdateRequestTo) {
        long commentId = commentUpdateRequestTo.id();
        Comment comment =
                commentRepository.findById(commentId).orElseThrow(() -> CommentNotFoundException.byId(commentId));

        long tweetId = commentUpdateRequestTo.tweetId();
        //Tweet tweet = new Tweet();
                //not tweetRepository.findById(commentId).orElseThrow(() -> CommentNotFoundException.byId(tweetId));
//        tweet.setId(tweetId);

        comment.setTweetId(commentUpdateRequestTo.tweetId());
        comment.setContent(commentUpdateRequestTo.content());

        return commentMapper.toCommentResponseTo(comment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.findById(id).orElseThrow(() -> CommentNotFoundException.byId(id));

        commentRepository.deleteById(id);
    }
}
