package by.bsuir.poit.dc.cassandra.api.controllers;

import by.bsuir.poit.dc.cassandra.dao.CommentByIdRepository;
import by.bsuir.poit.dc.cassandra.dao.CommentByTweetRepository;
import com.datastax.oss.driver.api.core.CqlSession;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;

/**
 * @author Name Surname
 * 
 */
@TestConfiguration
public class KafkaTestConfiguration {
    @MockBean
    public CqlSession cassandraSession;
    @MockBean
    public CassandraTemplate cassandraTemplate;
    @MockBean
    public CassandraConverter cassandraConverter;
    @MockBean
    public SpringLiquibase liquibase;
    @MockBean
    public CommentByIdRepository commentByIdRepository;
    @MockBean
    public CommentByTweetRepository commentByTweetRepository;
}
