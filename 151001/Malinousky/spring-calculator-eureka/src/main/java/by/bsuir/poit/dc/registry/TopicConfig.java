package by.bsuir.poit.dc.registry;

import lombok.Setter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author Name Surname
 * 
 */
@Setter
@Configuration
public class TopicConfig {
    @Value("${kafka-topics.input}")
    private String inputTopicName;
    @Value("${kafka-topics.output}")
    private String outputTopicName;

    @Bean
    public NewTopic inputTopic() {
	return TopicBuilder
		   .name(inputTopicName)
		   .build();
    }

    @Bean
    public NewTopic outputTopic() {
	return TopicBuilder
		   .name(outputTopicName)
		   .build();
    }
}
