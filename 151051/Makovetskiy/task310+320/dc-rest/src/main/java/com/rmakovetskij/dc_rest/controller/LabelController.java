package com.rmakovetskij.dc_rest.controller;

import com.rmakovetskij.dc_rest.model.dto.requests.LabelRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.LabelResponseTo;
import com.rmakovetskij.dc_rest.service.LabelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    // Создать новый стикер
    @PostMapping
    public ResponseEntity<LabelResponseTo> createLabel(@RequestBody @Valid LabelRequestTo labelRequestDto) {
        LabelResponseTo createdLabel = labelService.createLabel(labelRequestDto);
        return new ResponseEntity<>(createdLabel, HttpStatus.CREATED);
    }

    // Получить все стикеры
    @GetMapping
    public ResponseEntity<List<LabelResponseTo>> getAllLabels() {
        List<LabelResponseTo> labels = labelService.getAllLabels();
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    // Получить стикер по id
    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseTo> getLabelById(@PathVariable Long id) {
        LabelResponseTo label = labelService.getLabelById(id);
        return new ResponseEntity<>(label, HttpStatus.OK);
    }

    // Обновить стикер по id
    @PutMapping("/{id}")
    public ResponseEntity<LabelResponseTo> updateLabel(@PathVariable Long id, @RequestBody @Valid LabelRequestTo labelRequestDto) {
        LabelResponseTo updatedLabel = labelService.updateLabel(id, labelRequestDto);
        return new ResponseEntity<>(updatedLabel, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LabelResponseTo> updateMessage(@RequestBody @Valid LabelRequestTo labelRequestDto) {
        if (labelRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Используем ID из тела запроса для обновления
        LabelResponseTo updatedLabelResponseDto = labelService.updateLabel(labelRequestDto.getId(), labelRequestDto);
        return new ResponseEntity<>(updatedLabelResponseDto, HttpStatus.OK);
    }


    // Удалить стикер по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id) {
        try{
        labelService.deleteLabel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
