package com.example.rvlab1.controller;

import com.example.rvlab1.mapper.LabelMapper;
import com.example.rvlab1.model.dto.request.LabelRequestTo;
import com.example.rvlab1.model.dto.request.LabelRequestWithIdTo;
import com.example.rvlab1.model.dto.response.LabelResponseTo;
import com.example.rvlab1.model.service.Issue;
import com.example.rvlab1.model.service.Label;
import com.example.rvlab1.service.IssueService;
import com.example.rvlab1.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1.0/")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;
    private final IssueService issueService;
    private final LabelMapper labelMapper;

    @GetMapping(value = "/labels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LabelResponseTo>> getAllLabels() {
        List<LabelResponseTo> labelResponseToList = labelService.getAll().stream()
                .map(label -> {
                    LabelResponseTo labelResponseTo = labelMapper.mapToResponseTo(label);
                    labelResponseTo.setIssueId(label.getIssues().stream().findFirst().map(Issue::getId).orElse(null));
                    return labelResponseTo;
                }).toList();
        return ResponseEntity.ok(labelResponseToList);
    }

    @PostMapping(value = "/labels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabelResponseTo> createLabel(@RequestBody LabelRequestTo labelRequestTo) {
        Label label = labelMapper.mapToBO(labelRequestTo);
        if (Objects.nonNull(labelRequestTo.getIssueId())) {
            label.getIssues().add(issueService.findById(labelRequestTo.getIssueId()));
        }
        label = labelService.saveLabel(label);
        LabelResponseTo labelResponseTo = labelMapper.mapToResponseTo(label);
        if (!CollectionUtils.isEmpty(label.getIssues())) {
            labelResponseTo.setIssueId(label.getIssues().stream().findFirst().orElseThrow().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(labelResponseTo);
    }

    @DeleteMapping(value = "/labels/{labelId}")
    public ResponseEntity<Void> deleteLabelById(@PathVariable("labelId") Long labelId) {
        Label label = labelService.findById(labelId);
        labelService.deleteLabel(label);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/labels/{labelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabelResponseTo> getLabelById(@PathVariable("labelId") Long labelId) {
        Label label = labelService.findById(labelId);
        LabelResponseTo labelResponseTo = labelMapper.mapToResponseTo(label);
        labelResponseTo.setIssueId(label.getIssues().stream().findFirst().map(Issue::getId).orElse(null));
        return ResponseEntity.ok(labelResponseTo);
    }

    @PutMapping(value = "/labels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabelResponseTo> updateLabelById(@RequestBody LabelRequestWithIdTo labelRequestTo) {
        Label label = labelService.findById(labelRequestTo.getId());
        labelMapper.updateLabelRequestToToLabelBo(labelRequestTo, label);
        if (Objects.nonNull(labelRequestTo.getIssueId())) {
            label.setIssues(Set.of(issueService.findById(labelRequestTo.getIssueId())));
        }
        label = labelService.saveLabel(label);
        LabelResponseTo labelResponseTo = labelMapper.mapToResponseTo(label);
        if (!CollectionUtils.isEmpty(label.getIssues())) {
            labelResponseTo.setIssueId(label.getIssues().stream().findFirst().map(Issue::getId).orElse(null));
        }
        return ResponseEntity.ok(labelResponseTo);
    }
}
