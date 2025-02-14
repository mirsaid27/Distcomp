package by.ryabchikov.tweet_service.repository.impl;

import by.ryabchikov.tweet_service.entity.Mark;
import by.ryabchikov.tweet_service.repository.MarkRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Repository
public class MarkRepositoryImpl implements MarkRepository {
    private final Map<Long, Mark> marks = new HashMap<>();

    @Override
    public Mark save(Mark mark) {
        mark.setId(Math.abs(new Random().nextLong()));
        marks.put(mark.getId(), mark);
        return mark;
    }

    @Override
    public List<Mark> findAll() {
        return marks.values().stream().toList();
    }

    @Override
    public Optional<Mark> findById(Long id) {
        return Optional.ofNullable(marks.get(id));
    }

    @Override
    public void deleteById(Long id) {
        marks.remove(id);
    }
}