package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.StickerRepository;
import by.bsuir.publisher.model.entity.Sticker;
import by.bsuir.publisher.model.request.StickerRequestTo;
import by.bsuir.publisher.model.response.StickerResponseTo;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.exceptions.ResourceStateException;
import by.bsuir.publisher.service.mapper.StickerMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class StickerService implements RestService <StickerRequestTo, StickerResponseTo> {
    private final StickerRepository stickerRepository;

    private final StickerMapper stickerMapper;

    @Override
    public List<StickerResponseTo> findAll() {
        return stickerMapper.getListResponseTo(stickerRepository.findAll());
    }

    @Override
    public StickerResponseTo findById(Long id) {
        return stickerMapper.getResponseTo(stickerRepository
                .findById(id)
                .orElseThrow(() -> stickerNotFoundException(id)));
    }

    @Override
    public StickerResponseTo create(StickerRequestTo stickerTo) {
        return stickerMapper.getResponseTo(stickerRepository.save(stickerMapper.getSticker(stickerTo)));
    }

    @Override
    public StickerResponseTo update(StickerRequestTo stickerTo) {
        stickerRepository
                .findById(stickerMapper.getSticker(stickerTo).getId())
                .orElseThrow(() -> stickerNotFoundException(stickerMapper.getSticker(stickerTo).getId()));
        return stickerMapper.getResponseTo(stickerRepository.save(stickerMapper.getSticker(stickerTo)));
    }

    @Override
    public void removeById(Long id) {
        Sticker sticker = stickerRepository
                .findById(id)
                .orElseThrow(() -> stickerNotFoundException(id));
        stickerRepository.delete(sticker);
    }

    private static ResourceNotFoundException stickerNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find sticker with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 33);
    }

    private static ResourceStateException stickerStateException() {
        return new ResourceStateException("Failed to create/update sticker with specified credentials", HttpStatus.CONFLICT.value() * 100 + 34);
    }
}
