package com.example.rv.impl.sticker.Service;

import com.example.rv.api.exception.DuplicateEntityException;
import com.example.rv.api.exception.EntityNotFoundException;
import com.example.rv.impl.sticker.Sticker;
import com.example.rv.impl.sticker.StickerRepository;
import com.example.rv.impl.sticker.dto.StickerRequestTo;
import com.example.rv.impl.sticker.dto.StickerResponseTo;
import com.example.rv.impl.sticker.mapper.Impl.StickerMapperImpl;
import com.example.rv.impl.news.News;
import com.example.rv.impl.news.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StickerService {

    private final StickerRepository stickerRepository;
    private final NewsRepository newsRepository;

    private final StickerMapperImpl stickerMapper;
    private final String ENTITY_NAME = "sticker";

    public List<StickerResponseTo> getStickers() {
        List<Sticker> stickers = stickerRepository.findAll();
        List<StickerResponseTo> stickersTos = new ArrayList<>();
        for (var sticker: stickers) {
            stickersTos.add(stickerMapper.stickerToResponseTo(sticker));
        }
        return stickersTos;
    }


    public StickerResponseTo getStickerById(BigInteger id) throws EntityNotFoundException {
        Optional<Sticker> sticker = stickerRepository.findById(id);
        if (sticker.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return stickerMapper.stickerToResponseTo(sticker.get());
    }


    public StickerResponseTo saveSticker(StickerRequestTo sticker) throws DuplicateEntityException {
        List<News> news = new ArrayList<>();
        if (sticker.getTweetIds() != null) {
            news = newsRepository.findAllById(sticker.getTweetIds());
        }
        Sticker entity = stickerMapper.dtoToEntity(sticker, news);
        try {
            Sticker savedSticker = stickerRepository.save(entity);
            return stickerMapper.stickerToResponseTo(savedSticker);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }

    }

    public StickerResponseTo updateSticker(StickerRequestTo stickerRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        Optional<Sticker> stickerEntity = stickerRepository.findById(stickerRequestTo.getId());
        if (stickerEntity.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, stickerRequestTo.getId());
        }
        List<News> news = new ArrayList<>();
        if (stickerRequestTo.getTweetIds() != null) {
            news = newsRepository.findAllById(stickerRequestTo.getTweetIds());
        }
        try {
            Sticker sticker = stickerRepository.save(stickerMapper.dtoToEntity(stickerRequestTo, news));
            return stickerMapper.stickerToResponseTo(sticker);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    public void deleteStickerById(BigInteger id) throws EntityNotFoundException {
        if (stickerRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        stickerRepository.deleteById(id);
    }

}
