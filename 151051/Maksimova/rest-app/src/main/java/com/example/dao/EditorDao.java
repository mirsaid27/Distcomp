package com.example.dao;

import com.example.entities.Editor;

import java.util.Optional;

public interface EditorDao extends CrudDao<Editor> {
    Optional<Editor> getEditorByIssueId(long issueId);
}
