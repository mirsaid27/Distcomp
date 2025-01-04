package com.example.services;

import com.example.api.dto.StickerRequestTo;
import com.example.api.dto.StickerResponseTo;
import com.example.dao.StickerDao;
import com.example.entities.Sticker;
import com.example.exceptions.DeleteException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.UpdateException;
import com.example.mapper.StickerMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class StickerService {

    @Autowired
    private StickerMapper stickerMapper;

    @Autowired
    private StickerDao stickerDao;

    public StickerResponseTo getStickerById(@Min(0) Long id) throws NotFoundException {
        Optional<Sticker> sticker = stickerDao.findById(id);
        return sticker.map(stickerMapper::StickerToStickerResponse)
                .orElseThrow(() -> new NotFoundException("Sticker not found!", 40004L));
    }

    public List<StickerResponseTo> getStickers() {
        return stickerDao.findAll()
                .stream()
                .map(stickerMapper::StickerToStickerResponse)
                .collect(Collectors.toList());
    }

    public StickerResponseTo saveSticker(@Valid StickerRequestTo sticker) {
        Sticker stickerToSave = stickerMapper.StickerRequestToSticker(sticker);
        return stickerMapper.StickerToStickerResponse(stickerDao.save(stickerToSave));
    }

    public void deleteSticker(@Min(0) Long id) throws DeleteException {
        stickerDao.delete(id);
    }

    public StickerResponseTo updateSticker(@Valid StickerRequestTo sticker) throws UpdateException {
        Sticker stickerToUpdate = stickerMapper.StickerRequestToSticker(sticker);
        return stickerMapper.StickerToStickerResponse(stickerDao.update(stickerToUpdate));
    }
}
