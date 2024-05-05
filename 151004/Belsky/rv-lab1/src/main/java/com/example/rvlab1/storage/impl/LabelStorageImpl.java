package com.example.rvlab1.storage.impl;

import com.example.rvlab1.mapper.LabelMapper;
import com.example.rvlab1.model.service.Label;
import com.example.rvlab1.repository.LabelRepository;
import com.example.rvlab1.storage.LabelStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LabelStorageImpl implements LabelStorage {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public List<Label> findAll() {
        return labelRepository.findAll().stream()
                .map(labelMapper::mapToBO).toList();
    }

    @Override
    public Label save(Label label) {
        return labelMapper.mapToBO(labelRepository.save(labelMapper.mapToEntity(label)));
    }

    @Override
    public Optional<Label> findById(Long id) {
        return labelRepository.findById(id)
                .map(labelMapper::mapToBO);
    }

    @Override
    public void delete(Label label) {
        labelRepository.deleteById(label.getId());
    }
}
