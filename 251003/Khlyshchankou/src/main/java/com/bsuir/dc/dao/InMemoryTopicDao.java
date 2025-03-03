package com.bsuir.dc.dao;

import com.bsuir.dc.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTopicDao {
    private final Map<Long, Topic> topics = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Topic save(Topic topic) {
        if (topic.getId() == 0) {
            topic.setId(idGenerator.getAndIncrement());
        }
        topics.put(topic.getId(), topic);
        return topic;
    }

    public List<Topic> findAll() {
        return new ArrayList<>(topics.values());
    }
    public Optional<Topic> findById(long id) {
        return Optional.ofNullable(topics.get(id));
    }
    public void deleteById(long id) {
        topics.remove(id);
    }
}
