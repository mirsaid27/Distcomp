package com.example.rvlab1.service;

import com.example.rvlab1.model.service.Label;

import java.util.List;

public interface LabelService {
    List<Label> getAll();

    Label saveLabel(Label label);

    Label findById(Long labelId);

    void deleteLabel(Label label);
}
