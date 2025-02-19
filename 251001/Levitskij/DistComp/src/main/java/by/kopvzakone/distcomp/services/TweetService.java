package by.kopvzakone.distcomp.services;

import by.kopvzakone.distcomp.dto.*;
import by.kopvzakone.distcomp.repositories.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TweetService {
    public final TweetRepository repImpl;
    @Qualifier("tweetMapper")
    public final TweetMapper mapper;

    public List<TweetResponseTo> getAll() {
        return repImpl.getAll().map(mapper::out).toList();
    }
    public TweetResponseTo getById(Long id) {
        return repImpl.get(id).map(mapper::out).orElseThrow();
    }
    public TweetResponseTo create(TweetRequestTo req) {
        return repImpl.create(mapper.in(req)).map(mapper::out).orElseThrow();
    }
    public TweetResponseTo update(TweetRequestTo req) {
        return repImpl.update(mapper.in(req)).map(mapper::out).orElseThrow();
    }
    public boolean delete(Long id) {
        return repImpl.delete(id);
    }
}

