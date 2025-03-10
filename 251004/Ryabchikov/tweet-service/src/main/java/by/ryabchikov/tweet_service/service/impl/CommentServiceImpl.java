//package by.ryabchikov.tweet_service.service.impl;
//
//import by.ryabchikov.tweet_service.dto.comment.CommentRequestTo;
//import by.ryabchikov.tweet_service.dto.comment.CommentResponseTo;
//import by.ryabchikov.tweet_service.dto.comment.CommentUpdateRequestTo;
//import by.ryabchikov.tweet_service.entity.Comment;
//import by.ryabchikov.tweet_service.entity.Tweet;
//import by.ryabchikov.tweet_service.exception.CommentNotFoundException;
//import by.ryabchikov.tweet_service.mapper.CommentMapper;
//import by.ryabchikov.tweet_service.repository.CommentRepository;
//import by.ryabchikov.tweet_service.repository.TweetRepository;
//import by.ryabchikov.tweet_service.service.CommentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CommentServiceImpl implements CommentService {
//    private final CommentRepository commentRepository;
//    private final TweetRepository tweetRepository;
//    private final CommentMapper commentMapper;
//
//    @Override
//    @Transactional
//    public CommentResponseTo create(CommentRequestTo commentRequestTo) {
//        return commentMapper.toCommentResponseTo(
//                commentRepository.save(commentMapper.toComment(commentRequestTo))
//        );
//    }
//
//    @Override
//    @Transactional
//    public List<CommentResponseTo> readAll() {
//        return commentRepository.findAll().stream()
//                .map(commentMapper::toCommentResponseTo)
//                .toList();
//    }
//
//    @Override
//    @Transactional
//    public CommentResponseTo readById(Long id) {
//        return commentMapper.toCommentResponseTo(
//                commentRepository.findById(id).orElseThrow(() -> CommentNotFoundException.byId(id))
//        );
//    }
//
//    @Override
//    @Transactional
//    public CommentResponseTo update(CommentUpdateRequestTo commentUpdateRequestTo) {
//        long commentId = commentUpdateRequestTo.id();
//        Comment comment =
//                commentRepository.findById(commentId).orElseThrow(() -> CommentNotFoundException.byId(commentId));
//
//        long tweetId = commentUpdateRequestTo.tweetId();
//        Tweet tweet = new Tweet();
//                //tweetRepository.findById(commentId).orElseThrow(() -> CommentNotFoundException.byId(tweetId));
//        tweet.setId(tweetId);
//
//        comment.setTweet(tweet);
//        comment.setContent(commentUpdateRequestTo.content());
//
//        return commentMapper.toCommentResponseTo(comment);
//    }
//
//    @Override
//    @Transactional
//    public void deleteById(Long id) {
//        commentRepository.findById(id).orElseThrow(() -> CommentNotFoundException.byId(id));
//
//        commentRepository.deleteById(id);
//    }
//}
