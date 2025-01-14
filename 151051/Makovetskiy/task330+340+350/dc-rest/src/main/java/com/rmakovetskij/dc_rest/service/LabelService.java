package com.rmakovetskij.dc_rest.service;

import com.rmakovetskij.dc_rest.mapper.LabelMapper;
import com.rmakovetskij.dc_rest.model.Label;
import com.rmakovetskij.dc_rest.model.dto.requests.LabelRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.LabelResponseTo;
import com.rmakovetskij.dc_rest.repository.interfaces.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @CacheEvict(value = "labels", allEntries = true)
    public LabelResponseTo createLabel(LabelRequestTo labelRequestDto) {
        Label label = labelMapper.toEntity(labelRequestDto);
        label = labelRepository.save(label);
        return labelMapper.toResponse(label);
    }

    @Cacheable(value = "labels", key = "#id")
    public LabelResponseTo getLabelById(Long id) {
        Optional<Label> labelOptional = labelRepository.findById(id);
        return labelOptional.map(labelMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Label not found"));
    }

    @Cacheable(value = "labelsList")
    public List<LabelResponseTo> getAllLabels() {
        return labelRepository.findAll().stream()
                .map(labelMapper::toResponse)
                .toList();
    }
    @CacheEvict(value = "labels", key = "#id")
    public LabelResponseTo updateLabel(Long id, LabelRequestTo labelRequestDto) {
        Label existingLabel = labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label not found"));

        existingLabel.setName(labelRequestDto.getName());

        labelRepository.save(existingLabel);
        return labelMapper.toResponse(existingLabel);
    }

    @CacheEvict(value = "labels", key = "#id")
    public void deleteLabel(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found");
        }
        labelRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return labelRepository.existsById(id);
    }

}
