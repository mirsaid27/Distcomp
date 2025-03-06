package by.bsuir.distcomp.controller;

import by.bsuir.distcomp.dto.request.ReactionRequestTo;
import by.bsuir.distcomp.dto.response.ReactionResponseTo;
import by.bsuir.distcomp.entity.Reaction;
import by.bsuir.distcomp.service.ReactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/reactions")
public class ReactionController {
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @GetMapping
    ResponseEntity<List<ReactionResponseTo>> getAllReactions() {
        return new ResponseEntity<>(reactionService.getAllReactions(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id:\\d+}")
    ResponseEntity<ReactionResponseTo> getReactionById(@PathVariable Long id) {
        return new ResponseEntity<>(reactionService.getReactionById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReactionResponseTo> createAuthor(@RequestBody ReactionRequestTo reactionRequestTo) {
        return new ResponseEntity<>(reactionService.createReaction(reactionRequestTo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ReactionResponseTo> updateAuthor(@RequestBody ReactionRequestTo reactionRequestTo) {
        return new ResponseEntity<>(reactionService.updateReaction(reactionRequestTo), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<ReactionResponseTo> deleteAuthor(@PathVariable Long id) {
        reactionService.deleteReaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
