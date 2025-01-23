package com.example.dao.impl;

import com.example.dao.IssueDao;
import com.example.dao.StickerDao;
import com.example.entities.Issue;
import com.example.entities.Sticker;
import com.example.exceptions.DeleteException;
import com.example.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IssueDaoImpl implements IssueDao {

    private long counter = 0;
    private final Map<Long, Issue> map = new HashMap<>();

    @Autowired
    private StickerDao stickerDao;

    @Override
    public Issue save(Issue entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The issue has not been deleted", 40003L);
        }
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Issue> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Issue update(Issue updatedIssue) throws UpdateException {
        Long id = updatedIssue.getId();

        if (map.containsKey(id)) {
            Issue existingIssue = map.get(id);
            BeanUtils.copyProperties(updatedIssue, existingIssue);
            return existingIssue;
        } else {
            throw new UpdateException("Issue update failed", 40002L);
        }
    }

    @Override
    public Optional<Issue> getIssueByCriteria(String stickerName, Long stickerId, String title, String content) {
        for (Issue issue : map.values()) {
            if (matchesCriteria(issue, stickerName, stickerId, title, content)) {
                return Optional.of(issue);
            }
        }
        return Optional.empty();
    }

    private boolean matchesCriteria(Issue issue, String stickerName, Long stickerId, String title, String content) {

        if (stickerName != null || stickerId != null) {
            Optional<Sticker> stickerOptional = stickerDao.findById(stickerId);
            if (!stickerOptional.isPresent()) {
                return false;
            }
            Sticker sticker = stickerOptional.get();
            if (stickerName != null && !sticker.getName().equals(stickerName)) {
                return false;
            }
        }

        return true;
    }
}

