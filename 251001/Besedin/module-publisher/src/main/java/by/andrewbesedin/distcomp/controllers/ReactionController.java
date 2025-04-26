package by.andrewbesedin.distcomp.controllers;

import by.andrewbesedin.distcomp.dto.ReactionRequestTo;
import by.andrewbesedin.distcomp.dto.ReactionResponseTo;
import by.andrewbesedin.distcomp.services.ReactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/v1.0/notices")
@AllArgsConstructor
public class ReactionController {
    private final ReactionService serviceImpl;
    private final WebClient webClient;
    @GetMapping
    public Flux<ReactionResponseTo> getAll() {
        return webClient.get()
                .uri("/api/v1.0/notices")
                .retrieve()
                .bodyToFlux(ReactionResponseTo.class);
    }

    @GetMapping("/{id}")
    public Mono<ReactionResponseTo> getById(@PathVariable Long id) {
        return webClient.get()
                .uri("/api/v1.0/notices/{id}", id)
                .retrieve()
                .bodyToMono(ReactionResponseTo.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReactionResponseTo> create(@RequestBody @Valid ReactionRequestTo request) {
        return webClient.post()
                .uri("/api/v1.0/notices")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReactionResponseTo.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return webClient.delete()
                .uri("/api/v1.0/notices/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ReactionResponseTo> update(@RequestBody @Valid ReactionRequestTo request) {
        return webClient.put()
                .uri("/api/v1.0/notices")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReactionResponseTo.class);
    }
}

