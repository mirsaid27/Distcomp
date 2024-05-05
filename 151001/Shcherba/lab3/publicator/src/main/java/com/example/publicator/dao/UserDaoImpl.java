package com.example.publicator.dao;

import com.example.publicator.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Repository
public class UserDaoImpl implements UserDao {
    private static final Map<Integer, User> map = new HashMap();
    private static final AtomicInteger idHolder = new AtomicInteger();


    @Override
    public User create(User entity) {
        int userId = idHolder.incrementAndGet();
        entity.setId(userId);
        map.put(userId, entity);
        return entity;
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public User read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public User update(User entity, int id) {
        if (map.containsKey(id)) {
            entity.setId(id);
            map.put(id, entity);
            return entity;
        } else
            return null;
    }

    @Override
    public boolean delete(int id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return true;
        } else
            return false;
    }
}
