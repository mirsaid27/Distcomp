package by.bsuir.egor.Controllers;

import by.bsuir.egor.Service.EditorService;
import by.bsuir.egor.dto.EditorResponseTo;
import by.bsuir.egor.dto.EditorRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0", consumes = {"application/JSON"}, produces = {"application/JSON"})
public class EditorController {

    @Autowired
    private EditorService editorService;

    @GetMapping("/editors")
    public ResponseEntity<List<EditorResponseTo>> getAllEditors() {
        List<EditorResponseTo> editorResponseToList = editorService.getAll();

        return new ResponseEntity<>(editorResponseToList, HttpStatus.OK);
    }

    @GetMapping("/editors/{id}")
    public ResponseEntity<EditorResponseTo> getEditor(@PathVariable long id) {
        EditorResponseTo editorResponseTo = editorService.getById(id);
        return new ResponseEntity<>(editorResponseTo, editorResponseTo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("/editors")
    public ResponseEntity<EditorResponseTo> createEditor(@RequestBody EditorRequestTo userTo) {
        return  editorService.add(userTo);
    }

    @DeleteMapping("/editors/{id}")
    public ResponseEntity<EditorResponseTo> deleteEditor(@PathVariable long id) {
        return new ResponseEntity<>(null, editorService.deleteById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/editors")
    public ResponseEntity<EditorResponseTo> updateEditor(@RequestBody EditorRequestTo editorRequestTo) {
        EditorResponseTo editorResponseTo = editorService.update(editorRequestTo);
        return new ResponseEntity<>(editorResponseTo, editorResponseTo.getFirstname() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

}
