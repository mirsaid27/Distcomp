package by.kopvzakone.distcomp;

import by.kopvzakone.distcomp.repositories.Repo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DistCompApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistCompApplication.class, args);
	}

}
