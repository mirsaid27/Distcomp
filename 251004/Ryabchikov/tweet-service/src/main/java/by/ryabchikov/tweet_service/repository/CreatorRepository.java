package by.ryabchikov.tweet_service.repository;

import by.ryabchikov.tweet_service.entity.Creator;

import java.util.List;
import java.util.Optional;

public interface CreatorRepository {
    Creator save(Creator creator);

    List<Creator> findAll();

    Optional<Creator> findById(Long id);

    void deleteById(Long id);
}
