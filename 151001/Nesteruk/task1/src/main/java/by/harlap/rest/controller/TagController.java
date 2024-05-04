package by.harlap.rest.controller;

import by.harlap.rest.dto.request.CreateTagDto;
import by.harlap.rest.dto.request.UpdateTagDto;
import by.harlap.rest.dto.response.TagResponseDto;
import by.harlap.rest.facade.TagFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/tags")
public class TagController {

    private final TagFacade tagFacade;

    @GetMapping("/{id}")
    public TagResponseDto findTagById(@PathVariable("id") Long id) {
        return tagFacade.findById(id);
    }

    @GetMapping
    public List<TagResponseDto> findAll() {
        return tagFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagResponseDto saveTag(@RequestBody @Valid CreateTagDto tagRequest) {
        return tagFacade.save(tagRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long id) {
        tagFacade.delete(id);
    }

    @PutMapping
    public TagResponseDto updateTag(@RequestBody @Valid UpdateTagDto tagRequest) {
        return tagFacade.update(tagRequest);
    }
}
