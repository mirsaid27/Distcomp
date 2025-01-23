package by.bsuir.dc.impl.label;

import by.bsuir.dc.impl.label.model.LabelRequest;
import by.bsuir.dc.impl.label.model.LabelResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
@Data
@AllArgsConstructor
public class LabelController {
    private LabelService labelService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelResponse> findAll() {
        return labelService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelResponse findById(@PathVariable("id") long id) {
        return labelService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelResponse create(@RequestBody @Valid LabelRequest request) {
        return labelService.create(request);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public LabelResponse update(@RequestBody @Valid LabelRequest request) {
        return labelService.update(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        labelService.removeById(id);
    }
}
