package by.bsuir.poit.dc.rest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Name Surname
 * 
 */
@TestConfiguration

//@Testcontainers
public class TestContainersConfiguration {
    @Bean
    public Object mySuperBean() {
	System.out.println("I'm super bean");
	return new Object();
    }
//        @Bean(initMethod = "start", destroyMethod = "stop")
//    @Bean
//    @ServiceConnection
//    public PostgreSQLContainer<?> postgreSQLContainer() {
//	return new PostgreSQLContainer<>("postgres:16");
//    }
//    @Bean
//    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
//	var config = new HikariConfig();
//	config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
//	config.setUsername(postgreSQLContainer.getUsername());
//	config.setPassword(postgreSQLContainer.getPassword());
//	return new HikariDataSource(config);
//    }
}
