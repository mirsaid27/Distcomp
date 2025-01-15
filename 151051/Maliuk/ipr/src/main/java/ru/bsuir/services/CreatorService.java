package ru.bsuir.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.CreatorRequestTo;
import ru.bsuir.dto.response.CreatorResponseTo;
import ru.bsuir.entity.Creator;
import ru.bsuir.irepositories.ICreatorRepository;
import ru.bsuir.mapper.CreatorMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class CreatorService {

    private final ICreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    @CacheEvict(value = "creators", allEntries = true)
    public CreatorResponseTo createCreator(CreatorRequestTo creatorRequest) {

        Creator creator = creatorMapper.toEntity(creatorRequest);
        creatorRepository.save(creator);
        return creatorMapper.toDTO(creator);
    }

    @Cacheable(value = "creators", key = "#id")
    public CreatorResponseTo getCreatorById(Long id) {
        Optional<Creator> creatorOpt = creatorRepository.findById(id);
        return creatorOpt.map(creatorMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

    }

    @Cacheable(value = "creatorsList")
    public List<CreatorResponseTo> getAllCreator(){
        return  creatorRepository.findAll().stream()
                .map(creatorMapper::toDTO)
                .toList();
    }


    @CacheEvict(value = {"creators", "creatorsList"}, key = "#id", allEntries = true)
    public CreatorResponseTo updateCreator(Long id, CreatorRequestTo creatorRequest) {
        Creator creator = creatorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Creator not found"));

        if (creatorRequest.getLogin().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login must be at least 2 characters long");
        }
        creator.setLogin(creatorRequest.getLogin());
        creator.setPassword(creatorRequest.getPassword());
        creator.setFirstname(creatorRequest.getFirstname());
        creator.setLastname(creatorRequest.getLastname());

        creatorRepository.save(creator);
        return creatorMapper.toDTO(creator);
    }

    @CacheEvict(value = {"creators", "creatorsList"}, key = "#id", allEntries = true)
    public void deleteCreator(Long id) {

        if(!creatorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Creator not found");
        }
        creatorRepository.deleteById(id);
    }
    public boolean existById(Long id) { return creatorRepository.existsById(id);}
}
