package com.example.publisherservice.repository;

import com.example.publisherservice.model.Creator;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreatorRepository extends CrudRepository<Creator, Long> {

    Optional<Creator> findByLogin(String login);
}
