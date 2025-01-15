package ru.bsuir.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.LabelRequestTo;
import ru.bsuir.dto.response.LabelResponseTo;
import ru.bsuir.entity.Label;
import ru.bsuir.irepositories.ILabelRepository;
import ru.bsuir.mapper.LabelMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class LabelService {

    private final ILabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @CacheEvict(value = "labels", allEntries = true)
    public LabelResponseTo createLabel(LabelRequestTo labelRequest) {
        Label label = labelMapper.toEntity(labelRequest);
        label = labelRepository.save(label);
        return labelMapper.toDTO(label);
    }

    @Cacheable(value = "labels", key = "#id")
    public LabelResponseTo getLabelById(Long id) {
        Optional<Label> labelOpt = labelRepository.findById(id);
        return labelOpt.map(labelMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Label not found"));
    }

    @Cacheable(value = "labelsList")
    public List<LabelResponseTo> getAllLabels(){
        return labelRepository.findAll().stream()
                .map(labelMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = "labels", key = "#id")
    public LabelResponseTo updateLabel(Long id, LabelRequestTo labelRequest) {

        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label not found"));

        label.setName(labelRequest.getName());
        labelRepository.save(label);

        return labelMapper.toDTO(label);
    }

    @CacheEvict(value = "labels", key = "#id")
    public void deleteLabel(Long id) {
        if(!labelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found");
        }
        labelRepository.deleteById(id);
    }
}
