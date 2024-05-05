package by.bsuir.test_rw.controller;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.dto.issue.IssueRequestTO;
import by.bsuir.test_rw.model.dto.issue.IssueResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Issue;
import by.bsuir.test_rw.repository.implementations.in_memory.IssueInMemoryRepo;
import by.bsuir.test_rw.service.db_interaction.interfaces.IssueService;
import by.bsuir.test_rw.service.dto_convert.interfaces.IssueToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final IssueToConverter converter;

    @PostMapping()
    public ResponseEntity<IssueResponseTO> createIssue(@RequestBody @Valid IssueRequestTO issueRequestTO) {
        Issue issue = converter.convertToEntity(issueRequestTO);
        issueService.save(issue);
        IssueResponseTO issueResponseTO = converter.convertToDto(issue);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(issueResponseTO);
    }

    @GetMapping()
    public ResponseEntity<List<IssueResponseTO>> receiveAllIssues() {
        List<Issue> issueList = issueService.findAll();
        List<IssueResponseTO> responseList = issueList.stream()
                .map(converter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseTO> receiveIssueById(@PathVariable Long id) {
        Issue issue = issueService.findById(id);
        IssueResponseTO issueResponseTO = converter.convertToDto(issue);
        return ResponseEntity.ok(issueResponseTO);
    }

    @PutMapping()
    public ResponseEntity<IssueResponseTO> updateIssue(@RequestBody @Valid IssueRequestTO issueRequestTO) {
        Issue issue = converter.convertToEntity(issueRequestTO);
        issueService.update(issue);
        IssueResponseTO issueResponseTO = converter.convertToDto(issue);
        return ResponseEntity.ok(issueResponseTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssueById(@PathVariable Long id) {
        try {
            issueService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
