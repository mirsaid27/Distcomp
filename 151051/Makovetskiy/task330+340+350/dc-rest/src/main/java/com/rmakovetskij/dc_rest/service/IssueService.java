package com.rmakovetskij.dc_rest.service;

import com.rmakovetskij.dc_rest.mapper.IssueMapper;
import com.rmakovetskij.dc_rest.model.Issue;
import com.rmakovetskij.dc_rest.model.Editor;
import com.rmakovetskij.dc_rest.model.dto.requests.IssueRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.IssueResponseTo;
import com.rmakovetskij.dc_rest.repository.interfaces.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    @CacheEvict(value = "stories", allEntries = true)
    public IssueResponseTo createIssue(IssueRequestTo issueRequestDto) {
        Issue issue = issueMapper.toEntity(issueRequestDto);

        Editor editor = new Editor();
        editor.setId(issueRequestDto.getEditorId());
        issue.setEditor(editor);

        issue = issueRepository.save(issue);

        return issueMapper.toResponse(issue);
    }

    @Cacheable(value = "stories", key = "#id")
    public IssueResponseTo getIssueById(Long id) {
        Optional<Issue> issueOptional = issueRepository.findById(id);
        return issueOptional.map(issueMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    //@Cacheable(value = "storiesList")
    public List<IssueResponseTo> getAllStories() {
        return issueRepository.findAll().stream()
                .map(issueMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "stories", key = "#id")
    public IssueResponseTo updateIssue(Long id, IssueRequestTo issueRequestDto) {
        Issue existingIssue = issueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));

        if (issueRequestDto.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        existingIssue.setTitle(issueRequestDto.getTitle());
        existingIssue.setContent(issueRequestDto.getContent());

        issueRepository.save(existingIssue);
        return issueMapper.toResponse(existingIssue);
    }

    @CacheEvict(value = "stories", key = "#id")
    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new RuntimeException("Issue not found");
        }
        issueRepository.deleteById(id);
    }
}
