package by.molchan.Distcomp.controllers;

import by.molchan.Distcomp.DTOs.Requests.CommentRequestDTO;
import by.molchan.Distcomp.DTOs.Responses.CommentResponseDTO;
import by.molchan.Distcomp.services.CommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDTO createComment(@RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        return commentsService.save(commentRequestDTO);
      }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDTO> getAllComments() {
        return commentsService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDTO getCommentById(@PathVariable Long id) {
        return commentsService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long id){
        commentsService.deleteById(id);
    }

    // Non REST version for tests compliance
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDTO updateComment(@RequestBody @Valid CommentRequestDTO commentRequestDTO){
        return commentsService.update(commentRequestDTO);
    }
}
