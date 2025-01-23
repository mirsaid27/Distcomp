package org.example.tweetapi.repository;

import org.example.tweetapi.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
