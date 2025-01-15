package ru.bsuir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.entity.Editor;
import ru.bsuir.exceptions.IllegalFieldException;
import ru.bsuir.irepositories.IEditorRepository;
import ru.bsuir.mapper.EditorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class EditorService {

    private final IEditorRepository editorRepository;
    private final EditorMapper editorMapper;

    @Autowired
    public EditorService(IEditorRepository editorRepository, EditorMapper editorMapper) {
        this.editorRepository = editorRepository;
        this.editorMapper = editorMapper;
    }

    public EditorResponseTo createEditor(EditorRequestTo editorRequest) throws IllegalFieldException {
        if(editorRepository.existsByLogin(editorRequest.login())) {
            throw new IllegalFieldException("Duplicate login");
        }
        Editor editor = editorMapper.toEntity(editorRequest);
        editorRepository.save(editor);
        return editorMapper.toDTO(editor);
    }

    public Optional<EditorResponseTo> getEditorById(Long id) {
        Optional<Editor> editor = editorRepository.findById(id);
        return editor.map(editorMapper::toDTO);
    }

    public List<EditorResponseTo> getAllEditors(){
        return StreamSupport.stream(editorRepository.findAll().spliterator(), false)
                .map(editorMapper::toDTO)
                .toList();
    }


    public Optional<EditorResponseTo> updateEditor(Long id, EditorRequestTo editorRequest) {
        Optional<Editor> editor = editorRepository.findById(id);

        if (editor.isPresent()) {
            Editor data = editorMapper.toEntity(editorRequest);
            data.setId(editor.get().getId());

            editorRepository.save(data);
            return Optional.of(editorMapper.toDTO(data));
        }
        return Optional.empty();
    }

    public boolean deleteEditor(Long id) {

        if(editorRepository.existsById(id)) {
            editorRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
