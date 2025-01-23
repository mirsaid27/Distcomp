package com.example.service;

import com.example.repository.NewsRepository;
import com.example.request.NewsRequestTo;
import com.example.response.NewsResponseTo;
import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.ResourceStateException;
import com.example.mapper.NewsMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class NewsService implements IService<NewsRequestTo, NewsResponseTo> {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsResponseTo findById(Long id) {
        return newsRepository.getById(id).map(newsMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<NewsResponseTo> findAll() {
        return newsMapper.getListResponse(newsRepository.getAll());
    }

    @Override
    public NewsResponseTo create(NewsRequestTo request) {
        return newsRepository.save(newsMapper.getNews(request)).map(newsMapper::getResponse).orElseThrow(NewsService::createException);
    }

    @Override
    public NewsResponseTo update(NewsRequestTo request) {
        if (newsMapper.getNews(request).getId() == null)
        {
            throw findByIdException(newsMapper.getNews(request).getId());
        }

        return newsRepository.update(newsMapper.getNews(request)).map(newsMapper::getResponse).orElseThrow(NewsService::updateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!newsRepository.removeById(id)) {
            throw removeException();
        }
        return true;
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 41, "Can't find news by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 42, "Can't create news");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 43, "Can't update news");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 44, "Can't remove news");
    }
}