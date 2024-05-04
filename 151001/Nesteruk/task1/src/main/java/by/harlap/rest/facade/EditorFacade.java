package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateEditorDto;
import by.harlap.rest.dto.request.UpdateEditorDto;
import by.harlap.rest.dto.response.EditorResponseDto;
import by.harlap.rest.mapper.EditorMapper;
import by.harlap.rest.model.Editor;
import by.harlap.rest.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EditorFacade {

    private final EditorService editorService;
    private final EditorMapper editorMapper;

    public EditorResponseDto findById(Long id) {
        Editor editor = editorService.findById(id);
        return editorMapper.toEditorResponse(editor);
    }

    public List<EditorResponseDto> findAll() {
        List<Editor> editors = editorService.findAll();

        return editors.stream().map(editorMapper::toEditorResponse).toList();
    }

    public EditorResponseDto save(CreateEditorDto editorRequest) {
        Editor editor = editorMapper.toEditor(editorRequest);

        Editor savedEditor = editorService.save(editor);

        return editorMapper.toEditorResponse(savedEditor);
    }

    public EditorResponseDto update(UpdateEditorDto editorRequest) {
        Editor editor = editorService.findById(editorRequest.getId());

        Editor updatedEditor = editorMapper.toEditor(editorRequest, editor);

        return editorMapper.toEditorResponse(editorService.update(updatedEditor));
    }

    public void delete(Long id) {
        editorService.deleteById(id);
    }
}
