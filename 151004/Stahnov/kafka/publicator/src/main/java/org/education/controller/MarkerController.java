package org.education.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.education.bean.DTO.MarkerRequestTo;
import org.education.bean.DTO.MarkerResponseTo;
import org.education.bean.Marker;
import org.education.exception.AlreadyExists;
import org.education.exception.ErrorResponse;
import org.education.exception.IncorrectValuesException;
import org.education.exception.NoSuchMarker;
import org.education.service.MarkerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/markers")
@Tag(name = "Маркеры")
public class MarkerController {

    private final MarkerService markerService;

    private final ModelMapper modelMapper;

    @Autowired
    public MarkerController(MarkerService markerService, ModelMapper modelMapper) {
        this.markerService = markerService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Получение всех маркеров")
    @GetMapping
    public ResponseEntity<List<MarkerResponseTo>> getAllMarkers(){
        return new ResponseEntity<>(markerService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создание маркера")
    public ResponseEntity<MarkerResponseTo> createMarker(@RequestBody @Valid MarkerRequestTo markerRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(markerService.create(modelMapper.map(markerRequestTo, Marker.class)), HttpStatus.valueOf(201));
    }

    @PutMapping
    @Operation(summary = "Обновление маркера")
    public ResponseEntity<MarkerResponseTo> updateMarker(@RequestBody @Valid MarkerRequestTo markerRequestTo, @Parameter(hidden = true) BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IncorrectValuesException("Incorrect input values");
        }
        return new ResponseEntity<>(markerService.update(modelMapper.map(markerRequestTo, Marker.class)), HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаления маркера")
    public ResponseEntity<HttpStatus> deleteMarker(@PathVariable int id){
        markerService.delete(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение маркера по id")
    public ResponseEntity<MarkerResponseTo> getMarker(@PathVariable int id){
        return new ResponseEntity<>(markerService.getById(id), HttpStatus.valueOf(200));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NoSuchMarker exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(IncorrectValuesException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(AlreadyExists exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 403),HttpStatus.valueOf(403));
    }
}
