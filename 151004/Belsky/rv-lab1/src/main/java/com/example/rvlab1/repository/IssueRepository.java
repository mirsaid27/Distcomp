package com.example.rvlab1.repository;

import com.example.rvlab1.model.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
    boolean existsByTitle(String title);

    Stream<IssueEntity> findAllByContentContaining(String strToContain);
}
