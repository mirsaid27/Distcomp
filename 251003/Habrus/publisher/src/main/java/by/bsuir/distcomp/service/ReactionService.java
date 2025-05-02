package by.bsuir.distcomp.service;

import by.bsuir.distcomp.dto.mapper.ReactionMapper;
import by.bsuir.distcomp.dto.request.ReactionRequestTo;
import by.bsuir.distcomp.dto.response.ReactionResponseTo;
import by.bsuir.distcomp.repository.ReactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ReactionService(
            ReactionRepository reactionRepository,
            ReactionMapper reactionMapper,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.reactionRepository = reactionRepository;
        this.reactionMapper = reactionMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<ReactionResponseTo> getAllReactions() {
        return reactionRepository.findAll().stream().map(reactionMapper::toDto).toList();
    }

    public ReactionResponseTo getReactionById(Long id) {
        return reactionRepository.findById(id)
                .map(reactionMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Reaction with id: " + id + " not found"));
    }

    @Transactional
    public ReactionResponseTo createReaction(ReactionRequestTo reactionRequestTo) {
        ReactionResponseTo reactionResponseTo = reactionMapper.toDto(reactionRepository.save(reactionMapper.toEntity(reactionRequestTo)));
        reactionRequestTo.setId(reactionResponseTo.getId());
        sendToCreate(reactionRequestTo);
        return reactionResponseTo;
    }

    @Transactional
    public ReactionResponseTo updateReaction(ReactionRequestTo reactionRequestTo) {
        getReactionById(reactionRequestTo.getId());
        ReactionResponseTo reactionResponseTo = reactionMapper.toDto(reactionRepository.save(reactionMapper.toEntity(reactionRequestTo)));

        sendToUpdate(reactionRequestTo);
        return reactionResponseTo;
    }

    @Transactional
    public void deleteReaction(Long id) {
        getReactionById(id);
        sendToDelete(id);
        reactionRepository.deleteById(id);
    }

    public void sendToCreate(ReactionRequestTo req) {
        kafkaTemplate.send("create", req);
    }

    public void sendToUpdate(ReactionRequestTo req) {
        kafkaTemplate.send("update", req);
    }

    public void sendToDelete(Long id) {
        kafkaTemplate.send("delete", id);
    }

}
