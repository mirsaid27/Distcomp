package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateEditorDto;
import by.bsuir.poit.dc.rest.api.dto.response.EditorDto;
import by.bsuir.poit.dc.rest.services.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/editors")
public class EditorController {
    private final EditorService editorService;

    @PostMapping
    public ResponseEntity<EditorDto> createUser(
	@RequestBody @Validated(Create.class) UpdateEditorDto dto
    ) {
	var response = editorService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<EditorDto> getEditors(
	@RequestParam(value = "tweet_id", required = false) Long tweetId
    ) {
	if (tweetId == null) {
	    return editorService.getAll();
	}
	return List.of(
	    editorService.getEditorByTweetId(tweetId)
	);
    }

    @GetMapping("/{editorId}")
    public EditorDto getUserById(
	@PathVariable long editorId
    ) {
	return editorService.getById(editorId);
    }

    @PutMapping
    public EditorDto updateEditor(
	@RequestBody @Validated(Update.class) UpdateEditorDto dto
    ) {
	long editorId = dto.id();
	return editorService.update(editorId, dto);
    }

    @DeleteMapping("/{editorId}")
    public Object deleteUserById(
	@PathVariable long editorId
    ) {
	return editorService.deleteEditor(editorId);
    }
}
