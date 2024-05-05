package com.example.publicator.dao;

import com.example.publicator.model.Tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Repository
public class TweetDaoImpl implements TweetDao {
    Map<Integer, Tweet> map = new HashMap<>();
    private static final AtomicInteger idHolder = new AtomicInteger();

    @Override
    public Tweet create(Tweet entity) {
        int id = idHolder.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return entity;
    }

    @Override
    public List<Tweet> readAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Tweet read(int id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public Tweet update(Tweet entity, int id) {
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
