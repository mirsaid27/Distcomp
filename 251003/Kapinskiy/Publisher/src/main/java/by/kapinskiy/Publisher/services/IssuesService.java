package by.kapinskiy.Publisher.services;

import by.kapinskiy.Publisher.DTOs.Requests.IssueRequestDTO;
import by.kapinskiy.Publisher.DTOs.Responses.IssueResponseDTO;
import by.kapinskiy.Publisher.models.Issue;
import by.kapinskiy.Publisher.models.Tag;
import by.kapinskiy.Publisher.models.User;
import by.kapinskiy.Publisher.repositories.IssuesRepository;
import by.kapinskiy.Publisher.repositories.UsersRepository;
import by.kapinskiy.Publisher.utils.exceptions.NotFoundException;
import by.kapinskiy.Publisher.utils.mappers.IssuesMapper;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssuesService {

  private final IssuesRepository issuesRepository;
  private final UsersRepository usersRepository;
  private final IssuesMapper issuesMapper;


  @Autowired
  public IssuesService(IssuesRepository issuesRepository, UsersRepository usersRepository,
      IssuesMapper issuesMapper) {
    this.issuesRepository = issuesRepository;
    this.usersRepository = usersRepository;
    this.issuesMapper = issuesMapper;

  }

  private void setUser(Issue issue, long userId) {
    User user = usersRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User with such id does not exist"));
    issue.setUser(user);
  }

  @Transactional
  public IssueResponseDTO save(IssueRequestDTO issueRequestDTO) {
    Issue issue = issuesMapper.toIssue(issueRequestDTO);
    setUser(issue, issueRequestDTO.getUserId());
    if (!issueRequestDTO.getTags().isEmpty()) {
      issue.setTags(issueRequestDTO.getTags().stream().map(Tag::new).toList());
    }
    issue.setCreated(new Date());
    issue.setModified(new Date());
    return issuesMapper.toIssueResponse(issuesRepository.save(issue));
  }

  @Transactional(readOnly = true)
  public List<IssueResponseDTO> findAll() {
    return issuesMapper.toIssueResponseList(issuesRepository.findAll());
  }

  @Transactional(readOnly = true)
  @Cacheable(value = "issues", key = "#id")
  public IssueResponseDTO findById(long id) {
    return issuesMapper.toIssueResponse(
        issuesRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Issue with such id does not exist")));
  }

  @Transactional
  @CacheEvict(value = "issues", key = "#id")
  public void deleteById(long id) {
    if (!issuesRepository.existsById(id)) {
      throw new NotFoundException("Issue not found");
    }
    issuesRepository.deleteById(id);
  }

  @Transactional
  @CacheEvict(value = "issues", key = "#issueRequestDTO.id")
  public IssueResponseDTO update(IssueRequestDTO issueRequestDTO) {
    Issue issue = issuesMapper.toIssue(issueRequestDTO);
    Issue oldIssue = issuesRepository.findById(issue.getId())
        .orElseThrow(() -> new NotFoundException("Old issue not found"));
    Long userId = issueRequestDTO.getUserId();

    if (userId != null) {
      setUser(issue, userId);
    }
    issue.setCreated(oldIssue.getCreated());
    issue.setModified(new Date());
    return issuesMapper.toIssueResponse(issuesRepository.save(issue));
  }

  public boolean existsByTitle(String title) {
    return issuesRepository.existsByTitle(title);
  }

  public boolean existsById(Long id) {
    return issuesRepository.existsById(id);
  }
}
