package com.homel.user_stories.repository.Impl;

import com.homel.user_stories.model.Notice;
import com.homel.user_stories.repository.NoticeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryNoticeRepository implements NoticeRepository {
    private final Map<Long, Notice> notices = new HashMap<>();
    private long nextId = 1;

    @Override
    public Notice save(Notice notice) {
        if (notice.getId() == null) {
            notice.setId(nextId++);
        }
        notices.put(notice.getId(), notice);
        return notice;
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return Optional.ofNullable(notices.get(id));
    }

    @Override
    public List<Notice> findAll() {
        return new ArrayList<>(notices.values());
    }

    @Override
    public void deleteById(Long id) {
        notices.remove(id);
    }
}
