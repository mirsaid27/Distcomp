package by.bsuir.publisher.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {
    @Bean
    public NewTopic inTopic() {
        return TopicBuilder.name("in-topic")
                .partitions(3)
                .compact()
                .build();
    }
}