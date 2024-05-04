package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.StickerMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.StickerRepository;
import by.bsuir.poit.dc.rest.model.Sticker;
import by.bsuir.poit.dc.rest.services.StickerService;
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
public class StickerServiceImpl implements StickerService {
    private final StickerRepository stickerRepository;
    private final StickerMapper stickerMapper;

    @Override
    @CatchThrows(call = "newStickerCreationException")
    public StickerDto create(UpdateStickerDto dto) {
	Sticker entity = stickerMapper.toEntity(dto);
	Sticker savedEntity = stickerRepository.save(entity);
	return stickerMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newLabelModifyingException",
	args = "stickerId")
    public StickerDto update(long stickerId, UpdateStickerDto dto) {
	Sticker entity = stickerRepository
			   .findById(stickerId)
			   .orElseThrow(() -> newLabelNotFountException(stickerId));
	Sticker updatedEntity = stickerMapper.partialUpdate(entity, dto);
	Sticker savedEntity = stickerRepository.save(updatedEntity);
	return stickerMapper.toDto(savedEntity);
    }

    @Override
    public StickerDto getById(long stickerId) {
	return stickerRepository
		   .findById(stickerId)
		   .map(stickerMapper::toDto)
		   .orElseThrow(() -> newLabelNotFountException(stickerId));
    }

    @Override
    public StickerDto getByName(String name) {
	return stickerRepository
		   .findByName(name)
		   .map(stickerMapper::toDto)
		   .orElseThrow(() -> newLabelNotFountException(name));
    }

    @Override
    public List<StickerDto> getAll() {
	return stickerMapper.toDtoList(
	    stickerRepository.findAll()
	);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newLabelModifyingException",
	args = "stickerId")
    public PresenceDto delete(long stickerId) {
	return PresenceDto
		   .wrap(stickerRepository.existsById(stickerId))
		   .ifPresent(() -> stickerRepository.deleteById(stickerId));
    }

    @Keep
    private static ResourceModifyingException newLabelCreationException(
	Throwable e
    ) {
	final String msg = STR."Failed to create sticker by cause = \{e.getMessage()}";
	final String front = "Failed to create new sticker. Check dto";
	log.warn(msg);
	return new ResourceModifyingException(front, 75);

    }

    @Keep
    private static ResourceModifyingException newLabelModifyingException(
	long labelId,
	Throwable e) {
	final String frontMessage = STR."Failed to modify sticker by id=\{labelId}";
	final String msg = STR."Failed to modify sticker by id=\{labelId} with cause=\{e.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(frontMessage, 50);
    }

    @Keep
    private static ResourceNotFoundException newLabelNotFountException(String name) {
	final String msg = STR."Failed to find sticker by name = \{name}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 47);

    }

    @Keep
    private static ResourceNotFoundException newLabelNotFountException(long labelId) {
	final String msg = STR."Failed to find sticker by id = \{labelId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 48);
    }
}
