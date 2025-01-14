package com.rmakovetskij.dc_rest.service;

import com.rmakovetskij.dc_rest.mapper.EditorMapper;
import com.rmakovetskij.dc_rest.model.Editor;
import com.rmakovetskij.dc_rest.model.dto.requests.EditorRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.EditorResponseTo;
import com.rmakovetskij.dc_rest.repository.interfaces.EditorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EditorService {

    private final EditorRepository editorRepository;
    private final EditorMapper editorMapper;

    // Создать нового пользователя
    public EditorResponseTo createEditor(EditorRequestTo editorRequestDto) {
            Editor editor = editorMapper.toEntity(editorRequestDto);
            editor = editorRepository.save(editor);
            return editorMapper.toResponse(editor);
    }

    // Получить пользователя по id
    public EditorResponseTo getEditorById(Long id) {
        Optional<Editor> editorOptional = editorRepository.findById(id);
        return editorOptional.map(editorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Editor not found"));
    }

    // Получить всех пользователей
    public List<EditorResponseTo> getAllEditors() {
        return editorRepository.findAll().stream()
                .map(editorMapper::toResponse)
                .toList();
    }

    public EditorResponseTo updateEditor(Long id, EditorRequestTo editorRequestDto) {
        Editor existingEditor = editorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));

        // Проверка длины логина
        if (editorRequestDto.getLogin().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login must be at least 2 characters long");
        }

        existingEditor.setLogin(editorRequestDto.getLogin());
        existingEditor.setPassword(editorRequestDto.getPassword());
        existingEditor.setFirstname(editorRequestDto.getFirstname());
        existingEditor.setLastname(editorRequestDto.getLastname());

        editorRepository.save(existingEditor);
        return editorMapper.toResponse(existingEditor);
    }


    // Удалить пользователя по id
    public void deleteEditor(Long id) {
        editorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return editorRepository.existsById(id);
    }
}