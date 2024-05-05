package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateEditorDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.EditorDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
public interface EditorService {
    EditorDto create(@Valid UpdateEditorDto dto);

    EditorDto getById(long userId);

    EditorDto getEditorByTweetId(long tweetId);

    List<EditorDto> getAll();

    EditorDto update(long userId, @Valid UpdateEditorDto dto);

    /*
    return true if user was deleted
     */
    PresenceDto deleteEditor(long userId);
}
