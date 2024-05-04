package by.harlap.rest.service.impl;

import by.harlap.rest.exception.EntityNotFoundException;
import by.harlap.rest.model.Editor;
import by.harlap.rest.repository.AbstractRepository;
import by.harlap.rest.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EditorServiceImpl implements EditorService {

    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Editor with id '%d' doesn't exist";
    private final AbstractRepository <Editor, Long> authorRepository;

    @Override
    public Editor findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> {
            final String message = AUTHOR_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(AUTHOR_NOT_FOUND_MESSAGE));

        authorRepository.deleteById(id);
    }

    @Override
    public Editor save(Editor author) {
        return authorRepository.save(author);
    }

    @Override
    public Editor update(Editor author) {
        authorRepository.findById(author.getId()).orElseThrow(()-> new EntityNotFoundException(AUTHOR_NOT_FOUND_MESSAGE));

        return authorRepository.update(author);
    }

    @Override
    public List<Editor> findAll() {
        return authorRepository.findAll();
    }
}
