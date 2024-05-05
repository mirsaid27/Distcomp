package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.Creator;
import com.example.lab2.Model.Issue;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IssueRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private CreatorRepository creatorRepository;

    @Test
    public void testFindById(){
        Creator creator = new Creator(0,"1010", "1010101010", "vika", "loziuk");
        Creator resCr = creatorRepository.save(creator);

        Issue issue = new Issue(0,resCr,"1100","1111",null,null,null);
        Issue res = issueRepository.save(issue);
        assertEquals(issueRepository.findById(res.getId()), Optional.of(res));
    }
    @Test
    public void testSave(){
        Creator creator = new Creator(0,"10101", "1010101010", "vika", "loziuk");
        Creator resCr = creatorRepository.save(creator);

        Issue issue = new Issue(0,resCr,"1122","1111",null,null,null);
        issueRepository.save(issue);
        assertNotEquals(issue.getId(), 0);
    }
    @Test
    public void testDeleteById(){
        Creator creator = new Creator(0,"10102", "1010101010", "vika", "loziuk");
        Creator resCr = creatorRepository.save(creator);

        Issue issue = new Issue(0,resCr,"1133","1111",null,null,null);
        Issue res = issueRepository.save(issue);
        issueRepository.deleteById(res.getId());
        assertFalse(issueRepository.existsById(res.getId()));
    }

}