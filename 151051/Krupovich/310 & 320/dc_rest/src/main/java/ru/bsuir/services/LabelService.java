package ru.bsuir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.LabelRequestTo;
import ru.bsuir.dto.response.LabelResponseTo;
import ru.bsuir.entity.Label;
import ru.bsuir.irepositories.ILabelRepository;
import ru.bsuir.mapper.LabelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class LabelService {

    private final ILabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Autowired
    public LabelService(ILabelRepository labelRepository, LabelMapper labelMapper) {
        this.labelRepository = labelRepository;
        this.labelMapper = labelMapper;
    }

    public LabelResponseTo createLabel(LabelRequestTo labelRequest) {
        Label label = labelMapper.toEntity(labelRequest);
        labelRepository.save(label);
        return labelMapper.toDTO(label);
    }

    public Optional<LabelResponseTo> getLabelById(Long id) {
        Optional<Label> label = labelRepository.findById(id);
        return label.map(labelMapper::toDTO);
    }

    public List<LabelResponseTo> getAllLabels(){
        return StreamSupport.stream(labelRepository.findAll().spliterator(), false)
                .map(labelMapper::toDTO)
                .toList();
    }

    public Optional<LabelResponseTo> updateLabel(Long id, LabelRequestTo labelRequest) {
        Optional<Label> labelOpt = labelRepository.findById(id);
        if (labelOpt.isPresent()) {
            Label label = labelOpt.get();
            label.setName(labelRequest.name());
            labelRepository.save(label);
            return Optional.of(labelMapper.toDTO(label));
        }
        return Optional.empty();
    }

    public boolean deleteLabel(Long id) {
        if(labelRepository.existsById(id)) {
            labelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
