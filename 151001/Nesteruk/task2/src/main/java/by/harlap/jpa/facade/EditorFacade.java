package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateEditorDto;
import by.harlap.jpa.dto.request.UpdateEditorDto;
import by.harlap.jpa.dto.response.EditorResponseDto;
import by.harlap.jpa.mapper.EditorMapper;
import by.harlap.jpa.model.Editor;
import by.harlap.jpa.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EditorFacade {

    private final EditorService editorService;
    private final EditorMapper editorMapper;

    @Transactional(readOnly = true)
    public EditorResponseDto findById(Long id) {
        Editor editor = editorService.findById(id);
        return editorMapper.toEditorResponse(editor);
    }

    @Transactional(readOnly = true)
    public List<EditorResponseDto> findAll() {
        List<Editor> editors = editorService.findAll();

        return editors.stream().map(editorMapper::toEditorResponse).toList();
    }

    @Transactional
    public EditorResponseDto save(CreateEditorDto editorRequest) {
        Editor editor = editorMapper.toEditor(editorRequest);

        Editor savedEditor = editorService.save(editor);

        return editorMapper.toEditorResponse(savedEditor);
    }

    @Transactional
    public EditorResponseDto update(UpdateEditorDto editorRequest) {
        Editor editor = editorService.findById(editorRequest.getId());

        Editor updatedEditor = editorMapper.toEditor(editorRequest, editor);

        return editorMapper.toEditorResponse(editorService.update(updatedEditor));
    }

    @Transactional
    public void delete(Long id) {
        editorService.deleteById(id);
    }
}
