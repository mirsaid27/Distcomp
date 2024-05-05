package by.bsuir.publisher.services.impl;

import by.bsuir.publisher.domain.Sticker;
import by.bsuir.publisher.dto.requests.StickerRequestDto;
import by.bsuir.publisher.dto.requests.converters.StickerRequestConverter;
import by.bsuir.publisher.dto.responses.StickerResponseDto;
import by.bsuir.publisher.dto.responses.converters.CollectionStickerResponseConverter;
import by.bsuir.publisher.dto.responses.converters.StickerResponseConverter;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.Messages;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.repositories.StickerRepository;
import by.bsuir.publisher.services.StickerService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class StickerServiceImpl implements StickerService {

    private final StickerRepository stickerRepository;
    private final StickerRequestConverter stickerRequestConverter;
    private final StickerResponseConverter stickerResponseConverter;
    private final CollectionStickerResponseConverter collectionStickerResponseConverter;

    @Override
    @Validated
    public StickerResponseDto create(@Valid @NonNull StickerRequestDto dto) throws EntityExistsException {
        Optional<Sticker> sticker = dto.getId() == null ? Optional.empty() : stickerRepository.findById(dto.getId());
        if (sticker.isEmpty()) {
            return stickerResponseConverter.toDto(stickerRepository.save(stickerRequestConverter.fromDto(dto)));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<StickerResponseDto> read(@NonNull Long id) {
        return stickerRepository.findById(id).flatMap(sticker -> Optional.of(
                stickerResponseConverter.toDto(sticker)));
    }

    @Override
    @Validated
    public StickerResponseDto update(@Valid @NonNull StickerRequestDto dto) throws NoEntityExistsException {
        Optional<Sticker> sticker = dto.getId() == null || stickerRepository.findById(dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(stickerRequestConverter.fromDto(dto));
        return stickerResponseConverter.toDto(stickerRepository.save(sticker.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Sticker> sticker = stickerRepository.findById(id);
        stickerRepository.deleteById(sticker.map(Sticker::getId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
        return sticker.get().getId();
    }

    @Override
    public List<StickerResponseDto> readAll() {
        return collectionStickerResponseConverter.toListDto(stickerRepository.findAll());
    }
}
