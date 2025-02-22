package com.homel.user_stories.repository;

import com.homel.user_stories.model.Notice;
import com.homel.user_stories.model.Story;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {
    Notice save(Notice notification);
    Optional<Notice> findById(Long id);
    public List<Notice> findAll();
    void deleteById(Long id);
}
