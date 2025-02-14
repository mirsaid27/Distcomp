package by.ryabchikov.tweet_service.repository.impl;

import by.ryabchikov.tweet_service.entity.Creator;
import by.ryabchikov.tweet_service.repository.CreatorRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Repository
public class CreatorRepositoryImpl implements CreatorRepository {
    private final Map<Long, Creator> creators = new HashMap<>();

    @Override
    public Creator save(Creator creator) {
        creator.setId(Math.abs(new Random().nextLong()));
        creators.put(creator.getId(), creator);
        return creator;
    }

    @Override
    public List<Creator> findAll() {
        return creators.values().stream().toList();
    }

    @Override
    public Optional<Creator> findById(Long id) {
        return Optional.ofNullable(creators.get(id));
    }

    @Override
    public void deleteById(Long id) {
        creators.remove(id);
    }
}