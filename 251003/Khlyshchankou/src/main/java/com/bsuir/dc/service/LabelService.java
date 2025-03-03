package com.bsuir.dc.service;

import com.bsuir.dc.dao.InMemoryLabelDao;
import com.bsuir.dc.dto.request.LabelRequestTo;
import com.bsuir.dc.dto.response.LabelResponseTo;
import com.bsuir.dc.exception.EntityNotFoundException;
import com.bsuir.dc.model.Label;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService {
    private final ModelMapper modelMapper;
    private final InMemoryLabelDao labelDao;

    @Autowired
    public LabelService(ModelMapper modelMapper, InMemoryLabelDao labelDao) {
        this.modelMapper = modelMapper;
        this.labelDao = labelDao;
    }

    private Label convertToLabel(LabelRequestTo labelRequestTo) {
        return this.modelMapper.map(labelRequestTo, Label.class);
    }

    private LabelResponseTo convertToResponse(Label label) {
        return this.modelMapper.map(label, LabelResponseTo.class);
    }

    public LabelResponseTo create(LabelRequestTo labelRequestTo) {
        Label label = convertToLabel(labelRequestTo);
        labelDao.save(label);

        return convertToResponse(label);
    }

    public List<LabelResponseTo> findAll() {
        return labelDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public LabelResponseTo findById(long id) throws EntityNotFoundException {
        Label label = labelDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This label doesn't exist."));
        return convertToResponse(label);
    }

    public LabelResponseTo update(LabelRequestTo labelRequestTo) throws EntityNotFoundException{
        labelDao.findById(labelRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This label doesn't exist."));

        Label updatedLabel = convertToLabel(labelRequestTo);
        labelDao.save(updatedLabel);

        return convertToResponse(updatedLabel);
    }

    public void delete(long id) {
        labelDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This label doesn't exist."));
        labelDao.deleteById(id);
    }
}
