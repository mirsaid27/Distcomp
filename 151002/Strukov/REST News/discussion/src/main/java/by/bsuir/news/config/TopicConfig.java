package by.bsuir.news.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Value("${spring.kafka.topics.in-topic}")
    private String inTopic;
    @Value("${spring.kafka.topics.out-topic}")
    private String outTopic;
    @Value("${spring.kafka.topics.partitions}")
    private Integer partitions;

    @Bean
    public NewTopic OutTopic() {
        return TopicBuilder.name(outTopic).partitions(partitions).compact().build();
    }
}
