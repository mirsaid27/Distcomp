package ru.bsuir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.TweetRequestTo;
import ru.bsuir.dto.response.TweetResponseTo;
import ru.bsuir.entity.Editor;
import ru.bsuir.entity.Tweet;
import ru.bsuir.exceptions.IllegalFieldException;
import ru.bsuir.irepositories.IEditorRepository;
import ru.bsuir.irepositories.ITweetRepository;
import ru.bsuir.mapper.TweetMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TweetService {

    private final ITweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    private final IEditorRepository editorRepository;

    @Autowired
    public TweetService(ITweetRepository tweetRepository, TweetMapper tweetMapper, IEditorRepository editorRepository) {
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
        this.editorRepository = editorRepository;
    }

    public TweetResponseTo createTweet(TweetRequestTo tweetRequest) {
        Tweet tweet = tweetMapper.toEntity(tweetRequest);
        Optional<Editor> editorOpt = editorRepository.findById(tweetRequest.editorId());

        if(editorOpt.isPresent()) {
            tweet.setEditor(editorOpt.get());
        } else {
            throw new IllegalFieldException("Editor with id:" + tweetRequest.editorId() + " not found");
        }

        tweetRepository.save(tweet);
        return tweetMapper.toDTO(tweet);
    }

//    public TweetResponseTo createTweet(TweetRequestTo tweetRequest) {
//
//
//        Tweet tweet = tweetMapper.toEntity(tweetRequest);
//        Editor editor = new Editor();
//        editor.setId(tweetRequest.editorId());
//        tweet.setEditor(editor);
//
//        tweetRepository.save(tweet);
//        return tweetMapper.toDTO(tweet);
//    }

    public Optional<TweetResponseTo> getTweetById(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        return tweet.map(tweetMapper::toDTO);
    }

    public List<TweetResponseTo> getAllTweets(){
        return StreamSupport.stream(tweetRepository.findAll().spliterator(), false)
                .map(tweetMapper::toDTO)
                .toList();
    }
    public Optional<TweetResponseTo> updateTweet(Long id, TweetRequestTo tweetRequest) {
        Optional<Tweet> tweetOpt = tweetRepository.findById(id);
        if (tweetOpt.isPresent()) {

            Tweet existTweet = tweetOpt.get();

            existTweet.setTitle(tweetRequest.title());
            existTweet.setContent(tweetRequest.content());
            tweetRepository.save(existTweet);
            return Optional.of(tweetMapper.toDTO(existTweet));
        }
        return Optional.empty();
    }

    public boolean deleteTweet(Long id) {
        if(tweetRepository.existsById(id)) {
            tweetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
