package com.example.services;

import com.example.dao.EditorDao;
import com.example.api.dto.EditorRequestTo;
import com.example.api.dto.EditorResponseTo;
import com.example.api.dto.IssueResponseTo;
import com.example.entities.Editor;
import com.example.entities.Issue;
import com.example.exceptions.DeleteException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.UpdateException;
import com.example.mapper.EditorListMapper;
import com.example.mapper.EditorMapper;
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