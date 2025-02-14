package by.ryabchikov.tweet_service.service.impl;

import by.ryabchikov.tweet_service.dto.creator.CreatorRequestTo;
import by.ryabchikov.tweet_service.dto.creator.CreatorResponseTo;
import by.ryabchikov.tweet_service.dto.creator.CreatorUpdateRequestTo;
import by.ryabchikov.tweet_service.entity.Creator;
import by.ryabchikov.tweet_service.exception.CreatorNotFoundException;
import by.ryabchikov.tweet_service.mapper.CreatorMapper;
import by.ryabchikov.tweet_service.repository.CreatorRepository;
import by.ryabchikov.tweet_service.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {
    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    @Override
    public CreatorResponseTo create(CreatorRequestTo creatorRequestTo) {
        return creatorMapper.toCreatorResponseTo(
                creatorRepository.save(creatorMapper.toCreator(creatorRequestTo))
        );
    }

    @Override
    public List<CreatorResponseTo> readAll() {
        return creatorRepository.findAll().stream()
                .map(creatorMapper::toCreatorResponseTo)
                .toList();
    }

    @Override
    public CreatorResponseTo readById(Long id) {
        return creatorMapper.toCreatorResponseTo(
                creatorRepository.findById(id).orElseThrow(() -> CreatorNotFoundException.byId(id))
        );
    }

    @Override
    public CreatorResponseTo update(CreatorUpdateRequestTo creatorUpdateRequestTo) {
        long creatorId = creatorUpdateRequestTo.id();
        Creator creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> CreatorNotFoundException.byId(creatorId));

        creator.setLogin(creatorUpdateRequestTo.login());
        creator.setPassword(creatorUpdateRequestTo.password());
        creator.setFirstname(creatorUpdateRequestTo.firstname());
        creator.setLastname(creatorUpdateRequestTo.lastname());

        return creatorMapper.toCreatorResponseTo(creator);
    }

    @Override
    public void deleteById(Long id) {
        creatorRepository.findById(id)
                .orElseThrow(() -> CreatorNotFoundException.byId(id));

        creatorRepository.deleteById(id);
    }
}
