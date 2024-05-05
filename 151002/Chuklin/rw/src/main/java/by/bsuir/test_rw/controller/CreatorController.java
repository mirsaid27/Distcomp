package by.bsuir.test_rw.controller;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.dto.creator.CreatorRequestTO;
import by.bsuir.test_rw.model.dto.creator.CreatorResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Creator;
import by.bsuir.test_rw.service.db_interaction.interfaces.CreatorService;
import by.bsuir.test_rw.service.dto_convert.interfaces.CreatorToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/creators")
@RequiredArgsConstructor
public class CreatorController {
    private final CreatorService creatorService;
    private final CreatorToConverter converter;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            creatorService.deleteById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(
                HttpStatus.NO_CONTENT
        ).build();
    }

    @PutMapping()
    public ResponseEntity<CreatorResponseTO> updateCreator(@RequestBody @Valid CreatorRequestTO creatorRequestTO) {
        Creator creator =  converter.convertToEntity(creatorRequestTO);
        creatorService.update(creator);
        CreatorResponseTO userResponseTo = converter.convertToDto(creator);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseTO> receiveById(@PathVariable Long id) {
        CreatorResponseTO to = converter.convertToDto(creatorService.findById(id));
        return ResponseEntity.ok(to);
    }

    @GetMapping
    public ResponseEntity<List<CreatorResponseTO>> receiveAllCreators() {
        List<Creator> creators = creatorService.findAll();
        List<CreatorResponseTO> responseList = creators.stream().map(converter::convertToDto).toList();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTO> createCreator(@RequestBody @Valid CreatorRequestTO creatorRequestTO) {
        Creator creator = converter.convertToEntity(creatorRequestTO);
        creatorService.save(creator);
        CreatorResponseTO userResponseTo = converter.convertToDto(creator);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponseTo);
    }

}
