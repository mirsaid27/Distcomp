package com.rmakovetskij.dc_rest.service;

import com.rmakovetskij.dc_rest.mapper.IssueMapper;
import com.rmakovetskij.dc_rest.model.Issue;
import com.rmakovetskij.dc_rest.model.Editor;
import com.rmakovetskij.dc_rest.model.dto.requests.IssueRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.IssueResponseTo;
import com.rmakovetskij.dc_rest.repository.interfaces.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    public IssueResponseTo createIssue(IssueRequestTo issueRequestDto) {
        Issue issue = issueMapper.toEntity(issueRequestDto);

        // Устанавливаем пользователя по ID, переданному в DTO
        Editor editor = new Editor();  // Создаем экземпляр пользователя
        editor.setId(issueRequestDto.getEditorId());  // Устанавливаем ID из DTO
        issue.setEditor(editor);  // Устанавливаем пользователя в историю

        issue = issueRepository.save(issue);

        return issueMapper.toResponse(issue);
    }

    // Получить историю по id
    public IssueResponseTo getIssueById(Long id) {
        Optional<Issue> issueOptional = issueRepository.findById(id);
        return issueOptional.map(issueMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    // Получить все истории
    public List<IssueResponseTo> getAllIssues() {
        return issueRepository.findAll().stream()
                .map(issueMapper::toResponse)
                .toList();
    }

    public IssueResponseTo updateIssue(Long id, IssueRequestTo issueRequestDto) {
        Issue existingIssue = issueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));

        // Проверка длины заголовка
        if (issueRequestDto.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        existingIssue.setTitle(issueRequestDto.getTitle());
        existingIssue.setContent(issueRequestDto.getContent());

        issueRepository.save(existingIssue);
        return issueMapper.toResponse(existingIssue);
    }

    // Удалить историю по id
    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new RuntimeException("Issue not found");
        }
        issueRepository.deleteById(id);
    }
}
