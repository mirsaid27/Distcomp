package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.Tag;
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
public class TagRepositoryTest {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testFindById(){
        Tag tag = new Tag(0,"1234");
        Tag res = tagRepository.save(tag);
        assertEquals(tagRepository.findById(res.getId()), Optional.of(res));
    }

    @Test
    public void testSave(){
        Tag tag = new Tag(0,"1234");
        tagRepository.save(tag);
        assertNotEquals(tag.getId(), 0);
    }
    @Test
    public void testDeleteById(){
        Tag tag = new Tag(0,"1234");
        Tag res = tagRepository.save(tag);
        tagRepository.deleteById(res.getId());
        assertFalse(tagRepository.existsById(res.getId()));
    }

}