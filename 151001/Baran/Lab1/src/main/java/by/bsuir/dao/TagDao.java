package by.bsuir.dao;

import by.bsuir.entities.Tag;

import java.util.Optional;

public interface TagDao extends CrudDao<Tag> {
    Optional<Tag> getTagByIssueId (long issueId);
}
