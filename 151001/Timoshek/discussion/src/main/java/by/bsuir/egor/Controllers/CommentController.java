package by.bsuir.egor.Controllers;

import by.bsuir.egor.Service.CommentService;
import by.bsuir.egor.dto.CommentRequestTo;
import by.bsuir.egor.dto.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseTo>> getAllComment() {
        List<CommentResponseTo> commentResponseToList = commentService.getAll();
        return new ResponseEntity<>(commentResponseToList, HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseTo> getComment(@PathVariable Long id) {
        CommentResponseTo commentResponseTo = commentService.getById(id);
        return new ResponseEntity<>(commentResponseTo, commentResponseTo.getContent()==null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseTo> createComment(@RequestBody CommentRequestTo PostTo) {
        return commentService.add(PostTo);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseTo> deleteComment(@PathVariable long id) {
        return new ResponseEntity<>(null, commentService.deleteById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/comments")
    public ResponseEntity<CommentResponseTo> updateComment(@RequestBody CommentRequestTo CommentRequestTo) {
        CommentResponseTo CommentResponseTo = commentService.update(CommentRequestTo);
        return new ResponseEntity<>(CommentResponseTo, CommentResponseTo.getContent() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
