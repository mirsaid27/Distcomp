package com.example.services;

import com.example.dao.IssueDao;
import com.example.api.dto.IssueRequestTo;
import com.example.api.dto.IssueResponseTo;
import com.example.entities.Issue;
import com.example.exceptions.DeleteException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.UpdateException;
import com.example.mapper.IssueListMapper;
import com.example.mapper.IssueMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    IssueDao issueDao;
    @Autowired
    IssueListMapper issueListMapper;

    public IssueResponseTo getIssueById(@Min(0) Long id) throws NotFoundException{
        Optional<Issue> issue = issueDao.findById(id);
        return issue.map(value -> issueMapper.issueToIssueResponse(value)).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L));
    }

    public List<IssueResponseTo> getIssues() {
        return issueListMapper.toIssueResponseList(issueDao.findAll());
    }

    public IssueResponseTo saveIssue(@Valid IssueRequestTo issue) {
        Issue issueToSave = issueMapper.issueRequestToIssue(issue);
        return issueMapper.issueToIssueResponse(issueDao.save(issueToSave));
    }

    public void deleteIssue(@Min(0) Long id) throws DeleteException {
        issueDao.delete(id);
    }

    public IssueResponseTo updateIssue(@Valid IssueRequestTo issue) throws UpdateException {
        Issue issueToUpdate = issueMapper.issueRequestToIssue(issue);
        return issueMapper.issueToIssueResponse(issueDao.update(issueToUpdate));
    }

    public IssueResponseTo getIssueByCriteria(String TagName, Long TagId, String title, String content){
        return issueMapper.issueToIssueResponse(issueDao.getIssueByCriteria(TagName, TagId, title, content).orElseThrow(() -> new NotFoundException("Issue not found!", 40005L)));
    }
}
