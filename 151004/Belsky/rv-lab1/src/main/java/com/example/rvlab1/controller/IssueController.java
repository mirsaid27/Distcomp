package com.example.rvlab1.controller;

import com.example.rvlab1.mapper.IssueMapper;
import com.example.rvlab1.model.dto.request.IssueRequestTo;
import com.example.rvlab1.model.dto.request.IssueRequestWithIdTo;
import com.example.rvlab1.model.dto.response.IssueResponseTo;
import com.example.rvlab1.model.service.Issue;
import com.example.rvlab1.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/")
@RequiredArgsConstructor
public class IssueController {
    private final IssueMapper issueMapper;
    private final IssueService issueService;

    @GetMapping(value = "/issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IssueResponseTo>> getIssues() {
        return ResponseEntity.ok(issueService.getAll().stream().map(issueMapper::mapToResponseTo).toList());
    }

    @PostMapping(value = "/issues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponseTo> createIssue(@RequestBody IssueRequestTo issueRequestTo) {
        Issue issue = issueService.saveIssue(issueMapper.mapToBO(issueRequestTo));
        return ResponseEntity.status(HttpStatus.CREATED).body(issueMapper.mapToResponseTo(issue));
    }

    @DeleteMapping(value = "/issues/{issueId}")
    public ResponseEntity<Void> deleteIssueById(@PathVariable("issueId") Long issueId) {
        Issue issue = issueService.findById(issueId);
        issueService.deleteIssue(issue);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/issues/{issueId}")
    public ResponseEntity<IssueResponseTo> getIssueById(@PathVariable("issueId") Long issueId) {
        Issue issue = issueService.findById(issueId);
        return ResponseEntity.ok(issueMapper.mapToResponseTo(issue));
    }

    @PutMapping(value = "/issues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponseTo> updateIssueById(@RequestBody IssueRequestWithIdTo issueRequestTo) {
        Issue issue = issueService.findById(issueRequestTo.getId());
        issueMapper.updateIssueRequestToToIssueBo(issueRequestTo, issue);
        issue = issueService.saveIssue(issue);
        return ResponseEntity.ok(issueMapper.mapToResponseTo(issue));
    }
}
