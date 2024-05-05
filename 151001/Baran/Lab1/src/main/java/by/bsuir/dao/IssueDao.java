package by.bsuir.dao;

import by.bsuir.entities.Issue;

import java.util.Optional;

public interface IssueDao extends CrudDao<Issue> {
    Optional<Issue> getIssueByCriteria(String TagName, Long TagId, String title, String content);
}
