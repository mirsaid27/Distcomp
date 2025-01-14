package by.bsuir.dc.impl.author;

import by.bsuir.dc.impl.author.model.AuthorRequest;
import by.bsuir.dc.impl.author.model.AuthorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/authors")
@Data
@AllArgsConstructor
public class AuthorController {
    private AuthorService authorService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponse> findAll() {
        return authorService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponse findById(@PathVariable("id") long id) {
        return authorService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponse create(@RequestBody @Valid AuthorRequest request) {
        return authorService.create(request);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponse update(@RequestBody @Valid AuthorRequest request) {
        return authorService.update(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        authorService.removeById(id);
    }
}
