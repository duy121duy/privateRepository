package com.example.kafkaspring.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createNewTopic() {
        return TopicBuilder.name("newTopicTest")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic createNewTopicSendTo() {
        return TopicBuilder.name("newTopicSendTo")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic createUserTopic() {
        return TopicBuilder.name("reflectoring-user")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic createNumbersTopic() {
        return TopicBuilder.name("numbers")
                .build();
    }

    @Bean
    public NewTopic createSquaredNumbersTopic() {
        return TopicBuilder.name("squaredNumbers")
                .build();
    }
}
