package ru.bsuir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.StoryRequestTo;
import ru.bsuir.dto.response.StoryResponseTo;
import ru.bsuir.entity.Creator;
import ru.bsuir.entity.Story;
import ru.bsuir.exceptions.IllegalFieldException;
import ru.bsuir.irepositories.ICreatorRepository;
import ru.bsuir.irepositories.IStoryRepository;
import ru.bsuir.mapper.StoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class StoryService {

    private final IStoryRepository storyRepository;
    private final StoryMapper storyMapper;

    private final ICreatorRepository creatorRepository;

    @Autowired
    public StoryService(IStoryRepository storyRepository, StoryMapper storyMapper, ICreatorRepository creatorRepository) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
        this.creatorRepository = creatorRepository;
    }

    public StoryResponseTo createStory(StoryRequestTo storyRequest) {
        Story story = storyMapper.toEntity(storyRequest);
        Optional<Creator> creatorOpt = creatorRepository.findById(storyRequest.creatorId());

        if(creatorOpt.isPresent()) {
            story.setCreator(creatorOpt.get());
        } else {
            throw new IllegalFieldException("Creator with id:" + storyRequest.creatorId() + " not found");
        }

        storyRepository.save(story);
        return storyMapper.toDTO(story);
    }



    public Optional<StoryResponseTo> getStoryById(Long id) {
        Optional<Story> story = storyRepository.findById(id);
        return story.map(storyMapper::toDTO);
    }

    public List<StoryResponseTo> getAllStory(){
        return StreamSupport.stream(storyRepository.findAll().spliterator(), false)
                .map(storyMapper::toDTO)
                .toList();
    }
    public Optional<StoryResponseTo> updateStory(Long id, StoryRequestTo storyRequest) {
        Optional<Story> storyOpt = storyRepository.findById(id);
        if (storyOpt.isPresent()) {

            Story existStory = storyOpt.get();

            existStory.setTitle(storyRequest.title());
            existStory.setContent(storyRequest.content());
            storyRepository.save(existStory);
            return Optional.of(storyMapper.toDTO(existStory));
        }
        return Optional.empty();
    }

    public boolean deleteStory(Long id) {
        if(storyRepository.existsById(id)) {
            storyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
