package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
public interface StickerService {
    StickerDto create(@Valid UpdateStickerDto dto);

    StickerDto update(long stickerId, @Valid UpdateStickerDto dto);

    StickerDto getById(long stickerId);

    StickerDto getByName(String name);


    List<StickerDto> getAll();

    PresenceDto delete(long stickerId);
}
