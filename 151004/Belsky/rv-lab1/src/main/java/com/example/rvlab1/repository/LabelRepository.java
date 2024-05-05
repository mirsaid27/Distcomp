package com.example.rvlab1.repository;

import com.example.rvlab1.model.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<LabelEntity, Long> {
}
