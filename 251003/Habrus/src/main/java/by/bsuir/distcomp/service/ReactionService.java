package by.bsuir.distcomp.service;

import by.bsuir.distcomp.dto.mapper.MarkerMapper;
import by.bsuir.distcomp.dto.mapper.ReactionMapper;
import by.bsuir.distcomp.dto.request.ReactionRequestTo;
import by.bsuir.distcomp.dto.response.ReactionResponseTo;
import by.bsuir.distcomp.entity.Reaction;
import by.bsuir.distcomp.repository.ReactionRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;

    public ReactionService(ReactionRepository reactionRepository, ReactionMapper reactionMapper) {
        this.reactionRepository = reactionRepository;
        this.reactionMapper = reactionMapper;
    }

    public List<ReactionResponseTo> getAllReactions() {
        return reactionRepository.findAll().stream().map(reactionMapper::toDto).toList();
    }

    public ReactionResponseTo getReactionById(Long id) {
        return reactionRepository.findById(id)
                .map(reactionMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Reaction with id: " + id + " not found"));
    }

    public ReactionResponseTo createReaction(ReactionRequestTo reactionRequestTo) {
        return reactionMapper.toDto(reactionRepository.create(reactionMapper.toEntity(reactionRequestTo)));
    }

    public ReactionResponseTo updateReaction(ReactionRequestTo reactionRequestTo) {
        getReactionById(reactionRequestTo.getId());
        return reactionMapper.toDto(reactionRepository.update(reactionMapper.toEntity(reactionRequestTo)));
    }

    public void deleteReaction(Long id) {
        getReactionById(id);
        reactionRepository.deleteById(id);
    }

}
