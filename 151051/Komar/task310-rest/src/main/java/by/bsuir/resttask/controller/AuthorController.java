package by.bsuir.resttask.controller;

import by.bsuir.resttask.dto.request.AuthorRequestTo;
import by.bsuir.resttask.dto.response.AuthorResponseTo;
import by.bsuir.resttask.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService AUTHOR_SERVICE;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseTo getById(@PathVariable Long id) {
        return AUTHOR_SERVICE.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseTo> getAll() {
        return AUTHOR_SERVICE.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseTo save(@RequestBody @Valid AuthorRequestTo author) {
        return AUTHOR_SERVICE.save(author);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseTo update(@RequestBody @Valid AuthorRequestTo author) {
        return AUTHOR_SERVICE.update(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        AUTHOR_SERVICE.delete(id);
    }
}
