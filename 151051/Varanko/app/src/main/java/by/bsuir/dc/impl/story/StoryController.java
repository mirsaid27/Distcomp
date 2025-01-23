package by.bsuir.dc.impl.story;

import by.bsuir.dc.impl.story.model.StoryRequest;
import by.bsuir.dc.impl.story.model.StoryResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/storys")
@Data
@AllArgsConstructor
public class StoryController {
    private StoryService storyService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoryResponse> findAll() {
        return storyService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StoryResponse findById(@PathVariable("id") long id) {
        return storyService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoryResponse create(@RequestBody @Valid StoryRequest request) {
        return storyService.create(request);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StoryResponse update(@RequestBody @Valid StoryRequest request) {
        return storyService.update(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        storyService.removeById(id);
    }
}
