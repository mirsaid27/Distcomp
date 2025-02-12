package by.kapinskiy.Task310.services;


import by.kapinskiy.Task310.models.Issue;
import by.kapinskiy.Task310.models.User;
import by.kapinskiy.Task310.repositories.IssuesRepository;
import by.kapinskiy.Task310.repositories.UsersRepository;
import by.kapinskiy.Task310.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IssuesService {
    private final IssuesRepository issuesRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public IssuesService(IssuesRepository issuesRepository, UsersRepository usersRepository) {
        this.issuesRepository = issuesRepository;
        this.usersRepository = usersRepository;
    }

    public Issue save(Issue issue) {
        if (!usersRepository.existsById(issue.getUserId())) {
            throw new NotFoundException("User with such id does not exist");
        }
        issue.setCreated(new Date());
        issue.setModified(new Date());
        return issuesRepository.save(issue);
    }

    public List<Issue> findAll() {
        return issuesRepository.findAll();
    }

    public Issue findById(long id) {
        return issuesRepository.findById(id).orElseThrow(() -> new NotFoundException("Issue with such id does not exist"));
    }

    public void deleteById(long id) {
        if (!issuesRepository.existsById(id)) {
            throw new NotFoundException("Issue not found");
        }
        issuesRepository.deleteById(id);
    }

    public void up(long id, Issue issue) {
        issue.setId(id);
        update(issue);
    }

    public Issue update(Issue issue) {
        if (!issuesRepository.existsById(issue.getId())) {
            throw new NotFoundException("Issue not found");
        }

        issue.setModified(new Date());
        return issuesRepository.save(issue);
    }
}
