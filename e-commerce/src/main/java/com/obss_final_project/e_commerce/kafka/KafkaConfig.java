package com.obss_final_project.e_commerce.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic orderTopic() {
        return new NewTopic("order-success", 1, (short) 1);
    }
}
