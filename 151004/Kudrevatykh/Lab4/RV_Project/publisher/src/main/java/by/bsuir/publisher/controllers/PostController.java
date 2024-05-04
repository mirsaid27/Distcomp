package by.bsuir.publisher.controllers;

import by.bsuir.publisher.dto.requests.PostRequestDto;
import by.bsuir.publisher.dto.responses.PostResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.Messages;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.exceptions.ServiceException;
import by.bsuir.publisher.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody PostRequestDto post) throws EntityExistsException, ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> read(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(postService.read(id).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.readAll());
    }

    @PutMapping
    public ResponseEntity<PostResponseDto> update(@RequestBody PostRequestDto post) throws NoEntityExistsException, ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws NoEntityExistsException, ServiceException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.delete(id));
    }
}
