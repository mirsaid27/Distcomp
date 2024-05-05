package com.example.rv.api.Controllers;

import com.example.rv.api.exception.DuplicateEntityException;
import com.example.rv.api.exception.EntityNotFoundException;
import com.example.rv.impl.news.dto.NewsRequestTo;
import com.example.rv.impl.news.dto.NewsResponseTo;
import com.example.rv.impl.news.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponseTo> getNews() {
        return newsService.getNews();
    }

    @GetMapping("/{id}")
    public NewsResponseTo getNewsById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return newsService.getNewsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseTo createNews(@Valid @RequestBody NewsRequestTo newsRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        return newsService.saveNews(newsRequestTo);
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo updateNews(@Valid @RequestBody NewsRequestTo news) throws EntityNotFoundException, DuplicateEntityException {
        return newsService.updateNews(news);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable BigInteger id) throws EntityNotFoundException {
        newsService.deleteNews(id);
    }
}
