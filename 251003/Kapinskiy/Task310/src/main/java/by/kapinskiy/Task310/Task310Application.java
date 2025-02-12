package by.kapinskiy.Task310;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Task310Application {

	public static void main(String[] args) {
		SpringApplication.run(Task310Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
/*		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
				.setDestinationNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);
		return modelMapper;*/
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
