package by.bsuir.resttask.controller;

import by.bsuir.resttask.dto.request.NewsRequestTo;
import by.bsuir.resttask.dto.response.NewsResponseTo;
import by.bsuir.resttask.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService NEWS_SERVICE;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo getById(@PathVariable Long id) {
        return NEWS_SERVICE.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponseTo> getAll() {
        return NEWS_SERVICE.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseTo save(@RequestBody @Valid NewsRequestTo news) {
        return NEWS_SERVICE.save(news);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo update(@RequestBody @Valid NewsRequestTo news) {
        return NEWS_SERVICE.update(news);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        NEWS_SERVICE.delete(id);
    }
}
