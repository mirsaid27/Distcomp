package by.bsuir.test_rw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestRwApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestRwApplication.class, args);
    }

}
