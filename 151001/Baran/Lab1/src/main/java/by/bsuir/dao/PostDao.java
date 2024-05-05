package by.bsuir.dao;

import by.bsuir.entities.Post;

import java.util.Optional;

public interface PostDao extends CrudDao<Post> {
    Optional<Post> getPostByIssueId (long issueId);
}
