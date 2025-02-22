package by.kopvzakone.distcomp.controllers;

import by.kopvzakone.distcomp.dto.EditorRequestTo;
import by.kopvzakone.distcomp.dto.EditorResponseTo;
import by.kopvzakone.distcomp.services.EditorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/editors")
@AllArgsConstructor
public class EditorController {
    private final EditorService serviceImpl;
    @GetMapping
    public Collection<EditorResponseTo> getAll(){
        return serviceImpl.getAll();
    }
    @GetMapping("/{id}")
    public EditorResponseTo getById(@PathVariable Long id){
        return serviceImpl.getById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorResponseTo create(@RequestBody @Valid EditorRequestTo editorRequest){
        return serviceImpl.create(editorRequest);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        boolean delete = serviceImpl.delete(id);
        if(!delete)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo update(@RequestBody @Valid EditorRequestTo editorRequest){
        try{
            return serviceImpl.update(editorRequest);
        }catch( NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
