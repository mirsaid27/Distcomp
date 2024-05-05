package by.harlap.jpa.service.impl;

import by.harlap.jpa.exception.EntityNotFoundException;
import by.harlap.jpa.model.Editor;
import by.harlap.jpa.repository.impl.EditorRepository;
import by.harlap.jpa.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EditorServiceImpl implements EditorService {

    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Editor with id '%d' doesn't exist";
    private final EditorRepository editorRepository;

    @Override
    public Editor findById(Long id) {
        return editorRepository.findById(id).orElseThrow(() -> {
            final String message = AUTHOR_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        editorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(AUTHOR_NOT_FOUND_MESSAGE));

        editorRepository.deleteById(id);
    }

    @Override
    public Editor save(Editor editor) {
        return editorRepository.save(editor);
    }

    @Override
    public Editor update(Editor editor) {
        editorRepository.findById(editor.getId()).orElseThrow(()-> new EntityNotFoundException(AUTHOR_NOT_FOUND_MESSAGE));

        return editorRepository.save(editor);
    }

    @Override
    public List<Editor> findAll() {
        return editorRepository.findAll();
    }
}
