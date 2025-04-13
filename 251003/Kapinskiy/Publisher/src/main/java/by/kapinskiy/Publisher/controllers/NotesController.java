package by.kapinskiy.Publisher.controllers;

import by.kapinskiy.Publisher.DTOs.Requests.NoteRequestDTO;
import by.kapinskiy.Publisher.DTOs.Responses.NoteResponseDTO;
import by.kapinskiy.Publisher.utils.NoteValidator;
import by.kapinskiy.Publisher.utils.exceptions.CustomInformativeException;
import by.kapinskiy.Publisher.utils.exceptions.NotFoundException;
import by.kapinskiy.Publisher.utils.exceptions.ValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final RestClient restClient;
    private final NoteValidator notesValidator;
    @Autowired
    public NotesController(RestClient restClient, NoteValidator notesValidator) {
        this.restClient = restClient;
        this.notesValidator = notesValidator;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponseDTO createNote(@RequestBody @Valid NoteRequestDTO noteRequestDTO, BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()){
            notesValidator.validate(noteRequestDTO, bindingResult);
        }
        if (bindingResult.hasFieldErrors())
            throw new ValidationException(bindingResult);

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
    @ResponseStatus
    public ResponseEntity<Void> deleteNote(@PathVariable long id) {
        try {
            return restClient.delete()
                    .uri("/notes/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException ex) {
            throw new NotFoundException("Note not found");
        }
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
