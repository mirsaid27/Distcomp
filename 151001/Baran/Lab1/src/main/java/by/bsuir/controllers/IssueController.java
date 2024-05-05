package by.bsuir.controllers;

import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/issues")
public class IssueController {
    @Autowired
    IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueResponseTo>> getIssues() {
        return ResponseEntity.status(200).body(issueService.getIssues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseTo> getIssue(@PathVariable int id) {
        return ResponseEntity.status(200).body(issueService.getIssueById((long) id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<IssueResponseTo> saveIssue(@RequestBody IssueRequestTo issue) {
        IssueResponseTo savedIssue = issueService.saveIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIssue);
    }

    @PutMapping()
    public ResponseEntity<IssueResponseTo> updateIssue(@RequestBody IssueRequestTo issue) {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issue));
    }

    @GetMapping("/byCriteria")
    public ResponseEntity<IssueResponseTo> getIssueByCriteria(
            @RequestParam(required = false) String TagName,
            @RequestParam(required = false) Long TagId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueByCriteria(TagName, TagId, title, content));
    }
}