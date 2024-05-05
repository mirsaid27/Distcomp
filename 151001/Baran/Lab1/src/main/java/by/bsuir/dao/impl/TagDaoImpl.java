package by.bsuir.dao.impl;

import by.bsuir.dao.TagDao;
import by.bsuir.entities.Tag;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TagDaoImpl implements TagDao {

    private long counter = 0;
    private final Map<Long, Tag> map = new HashMap<>();

    @Override
    public Tag save(Tag entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Tag has not been deleted", 40003L);
        }
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Tag update(Tag updatedTag) throws UpdateException {
        Long id = updatedTag.getId();

        if (map.containsKey(id)) {
            Tag existingTag = map.get(id);
            BeanUtils.copyProperties(updatedTag, existingTag);
            return existingTag;
        } else {
            throw new UpdateException("Tag update failed", 40002L);
        }
    }

    @Override
    public Optional<Tag> getTagByIssueId(long issueId) {
        for (Tag Tag : map.values()) {
            if (Tag.getIssueId() != null) {
                if (Tag.getIssueId() == issueId) {
                    return Optional.of(Tag);
                }
            }
        }
        return Optional.empty();
    }
}
