package com.example.rv.impl.news.service;

import com.example.rv.api.exception.DuplicateEntityException;
import com.example.rv.api.exception.EntityNotFoundException;
import com.example.rv.impl.editor.Editor;
import com.example.rv.impl.editor.EditorRepository;
import com.example.rv.impl.news.News;
import com.example.rv.impl.news.NewsRepository;
import com.example.rv.impl.news.dto.NewsRequestTo;
import com.example.rv.impl.news.dto.NewsResponseTo;
import com.example.rv.impl.news.mapper.Impl.NewsMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final EditorRepository editorRepository;
    private final NewsMapperImpl newsMapper;

    private final String ENTITY_NAME = "tweet";

    public List<NewsResponseTo> getNews() {
        List<News> news = newsRepository.findAll();
        List<NewsResponseTo> newsResponseTos = new ArrayList<>();

        for (var item : news) {
            newsResponseTos.add(newsMapper.newsToResponseTo(item));
        }
        return newsResponseTos;
    }

    public NewsResponseTo getNewsById(BigInteger id) throws EntityNotFoundException {
        Optional<News> tweet = newsRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return newsMapper.newsToResponseTo(tweet.get());
    }

    public NewsResponseTo saveNews(NewsRequestTo newsRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        Optional<Editor> editor = editorRepository.findById(newsRequestTo.getEditorId());
        if (editor.isEmpty()) {
            throw new EntityNotFoundException("Editor", newsRequestTo.getEditorId());
        }

        if (newsRequestTo.getCreated() == null) {
            newsRequestTo.setCreated(new Date());
        }
        if (newsRequestTo.getModified() == null) {
            newsRequestTo.setModified(new Date());
        }
        if (isNumeric(newsRequestTo.getContent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "iss_content should be a string, not a number.");
        }
        News issueEntity = newsMapper.dtoToEntity(newsRequestTo, editor.get());

        try {
            News savedIssue = newsRepository.save(issueEntity);
            return newsMapper.newsToResponseTo(savedIssue);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "iss_content");
        }
    }

    public NewsResponseTo updateNews(NewsRequestTo newsRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        if (newsRepository.findById(newsRequestTo.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, newsRequestTo.getId());
        }

        Optional<Editor> editor = editorRepository.findById(newsRequestTo.getEditorId());

        if (editor.isEmpty()) {
            throw new EntityNotFoundException("Editor", newsRequestTo.getEditorId());
        }
        if (newsRequestTo.getCreated() == null) {
            newsRequestTo.setCreated(new Date());
        }
        if (newsRequestTo.getModified() == null) {
            newsRequestTo.setModified(new Date());
        }

        News news = newsMapper.dtoToEntity(newsRequestTo, editor.get());
        try {
            News savedNews = newsRepository.save(news);
            return newsMapper.newsToResponseTo(savedNews);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "tweet_content");
        }
    }

    public void deleteNews(BigInteger id) throws EntityNotFoundException {
        if (newsRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        newsRepository.deleteById(id);
    }
}
