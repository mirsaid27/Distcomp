package by.bsuir.egor.storage;

import by.bsuir.egor.Entity.Comment;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class CommentRepository implements InMemoryRepository<Comment> {
    private Map<Long, Comment> commentMemory = new ConcurrentHashMap<>();

    AtomicLong lastId = new AtomicLong();

    @Override
    public Comment findById(long id) {
        Comment comment = commentMemory.get(id);
        return comment;
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> commentList = new ArrayList<>();
        for (Long key : commentMemory.keySet()) {
            Comment comment = commentMemory.get(key);

            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    public Comment deleteById(long id) {
        Comment result = commentMemory.remove(id);
        return result;
    }

    @Override
    public boolean deleteAll() {
        commentMemory.clear();
        return true;
    }

    @Override
    public Comment insert(Comment insertObject) {
        long id = lastId.getAndIncrement();
        insertObject.setId(id);
        commentMemory.put(id, insertObject);
        return insertObject;
    }

    @Override
    public boolean updateById(Long id, Comment updatingComment) {
        boolean status = commentMemory.replace(id, commentMemory.get(id), updatingComment);
        return status;
    }

    @Override
    public boolean update(Comment updatingValue) {
        boolean status = commentMemory.replace(updatingValue.getId(), commentMemory.get(updatingValue.getId()), updatingValue);
        return status;
    }


}
