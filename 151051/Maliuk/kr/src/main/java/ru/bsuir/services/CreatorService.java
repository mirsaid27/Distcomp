package ru.bsuir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.CreatorRequestTo;
import ru.bsuir.dto.response.CreatorResponseTo;
import ru.bsuir.entity.Creator;
import ru.bsuir.exceptions.IllegalFieldException;
import ru.bsuir.irepositories.ICreatorRepository;
import ru.bsuir.mapper.CreatorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CreatorService {

    private final ICreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    @Autowired
    public CreatorService(ICreatorRepository creatorRepository, CreatorMapper creatorMapper) {
        this.creatorRepository = creatorRepository;
        this.creatorMapper = creatorMapper;
    }

    public CreatorResponseTo createCreator(CreatorRequestTo creatorRequest) throws IllegalFieldException {
        if(creatorRepository.existsByLogin(creatorRequest.login())) {
            throw new IllegalFieldException("Duplicate login");
        }
        Creator creator = creatorMapper.toEntity(creatorRequest);
        creatorRepository.save(creator);
        return creatorMapper.toDTO(creator);
    }

    public Optional<CreatorResponseTo> getEditorById(Long id) {
        Optional<Creator> creator = creatorRepository.findById(id);
        return creator.map(creatorMapper::toDTO);
    }

    public List<CreatorResponseTo> getAllCreators(){
        return StreamSupport.stream(creatorRepository.findAll().spliterator(), false)
                .map(creatorMapper::toDTO)
                .toList();
    }


    public Optional<CreatorResponseTo> updateCreator(Long id, CreatorRequestTo editorRequest) {
        Optional<Creator> creator = creatorRepository.findById(id);

        if (creator.isPresent()) {
            Creator data = creatorMapper.toEntity(editorRequest);
            data.setId(creator.get().getId());

            creatorRepository.save(data);
            return Optional.of(creatorMapper.toDTO(data));
        }
        return Optional.empty();
    }

    public boolean deleteCreator(Long id) {

        if(creatorRepository.existsById(id)) {
            creatorRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
