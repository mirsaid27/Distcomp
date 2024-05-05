package com.example.distributedcomputing.controller;


import com.example.distributedcomputing.model.request.IssueRequestTo;
import com.example.distributedcomputing.model.response.IssueResponseTo;
import com.example.distributedcomputing.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueResponseTo create(@RequestBody IssueRequestTo issueRequestTo) {
        return issueService.save(issueRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        issueService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<IssueResponseTo> getAll() {
        return issueService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo getById(@PathVariable Long id) {
        return issueService.getById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo update(@RequestBody IssueRequestTo issueRequestTo) {
        return issueService.update(issueRequestTo);
    }
}
