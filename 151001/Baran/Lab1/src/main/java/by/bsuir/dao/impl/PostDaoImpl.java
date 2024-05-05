package by.bsuir.dao.impl;

import by.bsuir.dao.PostDao;
import by.bsuir.entities.Post;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostDaoImpl implements PostDao {
    private long counter = 0;
    private final Map<Long, Post> map = new HashMap<>();

    @Override
    public Post save(Post entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Post has not been deleted", 40003L);
        }
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Post update(Post updatedPost) throws UpdateException {
        Long id = updatedPost.getId();

        if (map.containsKey(id)) {
            Post existingPost = map.get(id);
            BeanUtils.copyProperties(updatedPost, existingPost);
            return existingPost;
        } else {
            throw new UpdateException("Post update failed", 40002L);
        }
    }

    @Override
    public Optional<Post> getPostByIssueId(long issueId) {
        for (Post Post : map.values()) {
            if (Post.getIssueId() != null) {
                if (Post.getIssueId() == issueId) {
                    return Optional.of(Post);
                }
            }
        }
        return Optional.empty();
    }
}
