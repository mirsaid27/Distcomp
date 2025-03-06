package by.bsuir.distcomp.service;

import by.bsuir.distcomp.dto.mapper.MarkerMapper;
import by.bsuir.distcomp.dto.mapper.ReactionMapper;
import by.bsuir.distcomp.dto.request.ReactionRequestTo;
import by.bsuir.distcomp.dto.response.ReactionResponseTo;
import by.bsuir.distcomp.entity.Reaction;
import by.bsuir.distcomp.repository.ReactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return reactionMapper.toDto(reactionRepository.findById(id));
    }

    public ReactionResponseTo createReaction(ReactionRequestTo reactionRequestTo) {
        return reactionMapper.toDto(reactionRepository.create(reactionMapper.toEntity(reactionRequestTo)));
    }

    public ReactionResponseTo updateReaction(ReactionRequestTo reactionRequestTo) {
        return reactionMapper.toDto(reactionRepository.update(reactionMapper.toEntity(reactionRequestTo)));
    }

    public void deleteReaction(Long id) {
        reactionRepository.deleteById(id);
    }

}
