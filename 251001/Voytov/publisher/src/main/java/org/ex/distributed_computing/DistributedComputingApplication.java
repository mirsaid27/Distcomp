package org.ex.distributed_computing;

import org.ex.distributed_computing.config.DiscussionCommunicationProps;
import org.ex.distributed_computing.config.RestTemplateErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(DiscussionCommunicationProps.class)
public class DistributedComputingApplication {

  public static void main(String[] args) {
    SpringApplication.run(DistributedComputingApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new RestTemplateErrorHandler());
    return restTemplate;
  }
}
