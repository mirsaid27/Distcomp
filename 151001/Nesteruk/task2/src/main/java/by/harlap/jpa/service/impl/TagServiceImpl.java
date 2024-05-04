package by.harlap.jpa.service.impl;

import by.harlap.jpa.exception.EntityNotFoundException;
import by.harlap.jpa.model.Tag;
import by.harlap.jpa.repository.impl.TagRepository;
import by.harlap.jpa.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    public static final String TAG_NOT_FOUND_MESSAGE = "Tag with id '%d' doesn't exist";
    private final TagRepository tagRepository;

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> {
            final String message = TAG_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE));

        tagRepository.deleteById(id);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        tagRepository.findById(tag.getId()).orElseThrow(()-> new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE));

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
}
