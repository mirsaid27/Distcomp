package by.bsuir.publisher.controller;

import by.bsuir.publisher.util.HeadersUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/notes")
@RequiredArgsConstructor
public class NoteController {
    @Value("${app.notes-path}")
    private String notesPath;
    private final RestClient restClient;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader HttpHeaders headers) {
        return restClient.get()
                .uri(notesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader HttpHeaders headers,
                                    @RequestBody Object o) {

        return restClient.post()
                .uri(notesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .body(o)
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@RequestHeader HttpHeaders headers,
                                 @PathVariable Map<String, Object> pathVars) {
        return restClient.get()
                .uri(notesPath + "/{id}", pathVars)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                                    @RequestBody Object o) {
        return restClient.put()
                .uri(notesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .body(o)
                .retrieve()
                .toEntity(String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader HttpHeaders headers,
                                    @PathVariable Map<String, Object> pathVars) {
        return restClient.delete()
                .uri(notesPath + "/{id}", pathVars)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }
}