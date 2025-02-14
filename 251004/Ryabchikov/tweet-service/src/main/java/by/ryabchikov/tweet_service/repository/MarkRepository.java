package by.ryabchikov.tweet_service.repository;

import by.ryabchikov.tweet_service.entity.Mark;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {
    Mark save(Mark mark);

    List<Mark> findAll();

    Optional<Mark> findById(Long id);

    void deleteById(Long id);
}
