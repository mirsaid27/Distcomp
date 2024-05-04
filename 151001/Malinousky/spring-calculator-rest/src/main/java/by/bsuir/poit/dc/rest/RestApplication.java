package by.bsuir.poit.dc.rest;

import by.bsuir.poit.dc.LanguageQualityParser;
import by.bsuir.poit.dc.context.CatchThrowsBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author Name Surname
 * 
 */
@SpringBootApplication
@EnableCaching
public class RestApplication {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CatchThrowsBeanPostProcessor catchThrowsBeanPostProcessor() {
	return new CatchThrowsBeanPostProcessor();
    }

    @Bean
    public LanguageQualityParser languageQualityParser() {
	return LanguageQualityParser.withDefaults();
    }

    public static void main(String[] args) {
	SpringApplication.run(RestApplication.class, args);
    }
}
