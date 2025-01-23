package ru.bsuir.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.entity.Editor;
import ru.bsuir.irepositories.IEditorRepository;
import ru.bsuir.mapper.EditorMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class EditorService {

    private final IEditorRepository editorRepository;
    private final EditorMapper editorMapper;

    @CacheEvict(value = "editors", allEntries = true)
    public EditorResponseTo createEditor(EditorRequestTo editorRequest) {

        Editor editor = editorMapper.toEntity(editorRequest);
        editorRepository.save(editor);
        return editorMapper.toDTO(editor);
    }

    @Cacheable(value = "editors", key = "#id")
    public EditorResponseTo getEditorById(Long id) {
        Optional<Editor> editorOpt = editorRepository.findById(id);
        return editorOpt.map(editorMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Editor not found"));

    }

    @Cacheable(value = "editorsList")
    public List<EditorResponseTo> getAllEditors(){
        return  editorRepository.findAll().stream()
                .map(editorMapper::toDTO)
                .toList();
    }


    @CacheEvict(value = {"editors", "editorsList"}, key = "#id", allEntries = true)
    public EditorResponseTo updateEditor(Long id, EditorRequestTo editorRequest) {
        Editor editor = editorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));

        if (editorRequest.getLogin().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login must be at least 2 characters long");
        }
        editor.setLogin(editorRequest.getLogin());
        editor.setPassword(editorRequest.getPassword());
        editor.setFirstname(editorRequest.getFirstname());
        editor.setLastname(editorRequest.getLastname());

        editorRepository.save(editor);
        return editorMapper.toDTO(editor);
    }

    @CacheEvict(value = {"editors", "editorsList"}, key = "#id", allEntries = true)
    public void deleteEditor(Long id) {

        if(!editorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found");
        }
        editorRepository.deleteById(id);
    }
    public boolean existById(Long id) { return editorRepository.existsById(id);}
}
