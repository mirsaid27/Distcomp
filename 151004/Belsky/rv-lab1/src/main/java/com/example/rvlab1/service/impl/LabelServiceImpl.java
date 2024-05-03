package com.example.rvlab1.service.impl;

import com.example.rvlab1.exception.ServiceErrorCode;
import com.example.rvlab1.exception.ServiceException;
import com.example.rvlab1.model.service.Label;
import com.example.rvlab1.model.service.Post;
import com.example.rvlab1.service.LabelService;
import com.example.rvlab1.storage.LabelStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelStorage labelStorage;

    @Override
    public List<Label> getAll() {
        return labelStorage.findAll();
    }

    @Override
    public Label saveLabel(Label label) {
        validateLabelToSave(label);
        return labelStorage.save(label);
    }

    @Override
    public Label findById(Long labelId) {
        return labelStorage.findById(labelId)
                .orElseThrow(() -> new ServiceException("Label not found", ServiceErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public void deleteLabel(Label label) {
        label = saveLabel(label.setIssues(null));
        labelStorage.delete(label);
    }

    private void validateLabelToSave(Label label) {
        if (label.getName().length() < 2 || label.getName().length() > 32) {
            throw new ServiceException("Label не валиден", ServiceErrorCode.BAD_REQUEST);
        }
    }
}
