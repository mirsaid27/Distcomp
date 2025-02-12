package by.kapinskiy.Task310.controllers;


import by.kapinskiy.Task310.DTOs.Requests.IssueRequestDTO;
import by.kapinskiy.Task310.DTOs.Requests.UserRequestDTO;
import by.kapinskiy.Task310.DTOs.Responses.IssueResponseDTO;
import by.kapinskiy.Task310.DTOs.Responses.UserResponseDTO;
import by.kapinskiy.Task310.models.Issue;
import by.kapinskiy.Task310.models.User;
import by.kapinskiy.Task310.services.IssuesService;
import by.kapinskiy.Task310.utils.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssuesController {
    private final Mapper mapper;
    private final IssuesService issuesService;

    @Autowired
    public IssuesController(IssuesService issuesService, Mapper mapper) {
        this.issuesService = issuesService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@RequestBody @Valid IssueRequestDTO issueRequestDTO) {
        Issue issue = mapper.issueRequestToIssue(issueRequestDTO);
        return new ResponseEntity<>(mapper.issueToIssueResponse(issuesService.save(issue)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IssueResponseDTO>> getAllIssues() {
        return new ResponseEntity<>(mapper.issuesToIssueResponses(issuesService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseDTO> getByIssueById(@PathVariable Long id) {
        return new ResponseEntity<>(mapper.issueToIssueResponse(issuesService.findById(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable long id){
        issuesService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // The same as in Users Put
    @PutMapping
    public ResponseEntity<IssueResponseDTO> updateUser(@RequestBody @Valid IssueRequestDTO issueRequestDTO){
        Issue updatedIssue = issuesService.update(mapper.issueRequestToIssue(issueRequestDTO));
        return new ResponseEntity<>(mapper.issueToIssueResponse(updatedIssue), HttpStatus.OK);
    }
}
