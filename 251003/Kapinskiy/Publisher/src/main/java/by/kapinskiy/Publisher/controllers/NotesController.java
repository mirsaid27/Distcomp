package by.kapinskiy.Publisher.controllers;

import by.kapinskiy.Publisher.DTOs.Requests.NoteRequestDTO;
import by.kapinskiy.Publisher.DTOs.Responses.NoteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final RestClient restClient;

    @Autowired
    public NotesController(RestClient restClient) {
        this.restClient = restClient;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponseDTO createNote(@RequestBody @Valid NoteRequestDTO noteRequestDTO) {
        return restClient.post()
                .uri("/notes")
                .body(noteRequestDTO)
                .retrieve()
                .body(NoteResponseDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponseDTO> getAllNotes() {
        return restClient.get()
                .uri("/notes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<NoteResponseDTO>>() {});
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteResponseDTO getNoteById(@PathVariable Long id) {
        return restClient.get()
                .uri("/notes/{id}", id)
                .retrieve()
                .body(NoteResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable long id) {
        restClient.delete()
                .uri("/notes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NoteResponseDTO updateNote(@RequestBody @Valid NoteRequestDTO noteRequestDTO) {
        return restClient.put()
                .uri("/notes")
                .body(noteRequestDTO)
                .retrieve()
                .body(NoteResponseDTO.class);
    }
}
