package com.example.dao;

import com.example.entities.Post;

import java.util.Optional;

public interface PostDao extends CrudDao<Post> {
    Optional<Post> getPostByIssueId (long issueId);
}
