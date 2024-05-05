package by.bsuir.services;

import by.bsuir.dao.EditorDao;
import by.bsuir.dto.EditorRequestTo;
import by.bsuir.dto.EditorResponseTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.entities.Editor;
import by.bsuir.entities.Issue;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.EditorListMapper;
import by.bsuir.mapper.EditorMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class EditorService {
    @Autowired
    EditorMapper editorMapper;
    @Autowired
    EditorDao editorDao;
    @Autowired
    EditorListMapper editorListMapper;

    public EditorResponseTo getEditorById(@Min(0) Long id) throws NotFoundException{
        Optional<Editor> editor = editorDao.findById(id);
        return editor.map(value -> editorMapper.editorToEditorResponse(value)).orElseThrow(() -> new NotFoundException("Editor not found!", 40004L));
    }

    public List<EditorResponseTo> getEditors() {
        return editorListMapper.toEditorResponseList(editorDao.findAll());
    }

    public EditorResponseTo saveEditor(@Valid EditorRequestTo editor) {
        Editor editorToSave = editorMapper.editorRequestToEditor(editor);
        return editorMapper.editorToEditorResponse(editorDao.save(editorToSave));
    }

    public void deleteEditor(@Min(0) Long id) throws DeleteException {
        editorDao.delete(id);
    }

    public EditorResponseTo updateEditor(@Valid EditorRequestTo editor) throws UpdateException {
        Editor editorToUpdate = editorMapper.editorRequestToEditor(editor);
        return editorMapper.editorToEditorResponse(editorDao.update(editorToUpdate));
    }
    public EditorResponseTo getEditorByIssueId(@Min(0) Long issueId) throws NotFoundException{
        Optional<Editor> editor = editorDao.getEditorByIssueId(issueId);
        return editor.map(value -> editorMapper.editorToEditorResponse(value)).orElseThrow(() -> new NotFoundException("Editor not found!", 40004L));
    }
}
