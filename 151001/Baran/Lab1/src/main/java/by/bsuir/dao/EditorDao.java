package by.bsuir.dao;

import by.bsuir.entities.Editor;

import java.util.Optional;

public interface EditorDao extends CrudDao<Editor> {
    Optional<Editor> getEditorByIssueId(long issueId);
}
