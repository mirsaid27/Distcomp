package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.EditorMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateEditorDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.EditorDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.EditorRepository;
import by.bsuir.poit.dc.rest.model.Editor;
import by.bsuir.poit.dc.rest.services.EditorService;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Name Surname
 * 
 */

@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@RequiredArgsConstructor
public class EditorServiceImpl implements EditorService {
    private final EditorRepository editorRepository;
    private final EditorMapper editorMapper;

    @Override
    @CatchThrows(
	call = "newUserAlreadyExistsException"
    )
    public EditorDto create(UpdateEditorDto dto) {
	//todo: editorMapper should accept also hashing strategy to construct User entity
	Editor entity = editorMapper.toEntity(dto);
	Editor savedEntity = editorRepository.save(entity);
	return editorMapper.toDto(savedEntity);
    }

    @Override
    public EditorDto getById(long userId) {
	return editorRepository
		   .findById(userId)
		   .map(editorMapper::toDto)
		   .orElseThrow(() -> newUserNotFoundException(userId));
    }

    @Override
    public EditorDto getEditorByTweetId(long tweetId) {
	return editorRepository
		   .findByTweetId(tweetId)
		   .map(editorMapper::toDto)
		   .orElseThrow(() -> newUserNotFoundByTweetException(tweetId));
    }

    @Override
    public List<EditorDto> getAll() {
	return editorMapper.toDtoList(editorRepository.findAll());
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newUserModifyingException",
	args = "editorId")
    public EditorDto update(long userId, UpdateEditorDto dto) {
	Editor editor = editorRepository
			.findById(userId)
			.orElseThrow(() -> newUserNotFoundException(userId));
	Editor updatedEditor = editorMapper.partialUpdate(editor, dto);
	Editor savedEditor = editorRepository.save(updatedEditor);
	return editorMapper.toDto(savedEditor);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newUserModifyingException",
	args = "editorId")
    public PresenceDto deleteEditor(long userId) {
	return PresenceDto
		   .wrap(editorRepository.existsById(userId))
		   .ifPresent(() -> editorRepository.deleteById(userId));
    }

    @Keep
    private static ResourceModifyingException newUserModifyingException(long userId, Throwable e) {
	final String frontMsg = STR."Failed to modify editor by id=\{userId}";
	final String msg = STR."\{frontMsg} \{e.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(frontMsg, 51);
    }

    @Keep
    private static ResourceModifyingException newUserAlreadyExistsException(Throwable e) {
	final String msg = STR."Failed to create new editor. Actual cause =\{e.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(msg, 32);

    }

    private static ResourceNotFoundException newUserNotFoundByTweetException(long tweetId) {
	final String msg = STR."Failed to find any editor by tweet id = \{tweetId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 48);

    }

    private static ResourceNotFoundException newUserNotFoundException(long userId) {
	final String msg = STR."Failed to find editor by id=\{userId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 42);

    }
}
