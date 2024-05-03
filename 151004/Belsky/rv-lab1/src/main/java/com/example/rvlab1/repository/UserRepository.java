package com.example.rvlab1.repository;

import com.example.rvlab1.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLogin(String login);

    boolean existsById(Long id);
}
