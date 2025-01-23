package com.example.dao.impl;

import com.example.dao.EditorDao;
import com.example.dao.IssueDao;
import com.example.entities.Editor;
import com.example.entities.Issue;
import com.example.exceptions.DeleteException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EditorDaoImpl implements EditorDao {

    private long counter = 0;
    private final Map<Long, Editor> map = new HashMap<>();
    @Autowired
    private IssueDao issueDao;

    @Override
    public Editor save(Editor entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The editor has not been deleted", 40003L);
        }
    }

    @Override
    public List<Editor> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Editor> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Editor update(Editor updatedEditor) throws UpdateException {
        Long id = updatedEditor.getId();

        if (map.containsKey(id)) {
            Editor existingEditor = map.get(id);
            BeanUtils.copyProperties(updatedEditor, existingEditor);
            return existingEditor;
        } else {
            throw new UpdateException("Editor update failed", 40002L);
        }
    }

    @Override
    public Optional<Editor> getEditorByIssueId(long issueId) {
        Issue issue = issueDao.findById(issueId).orElseThrow(()-> new NotFoundException("Issue not found!", 40004L));
        return Optional.ofNullable(map.get(issue.getEditorId()));
    }
}