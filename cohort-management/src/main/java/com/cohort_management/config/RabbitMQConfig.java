package com.cohort_management.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String SPECIALIZATION_REQUEST_QUEUE = "specializationRequestQueue";

    @Bean
    public Queue specializationRequestQueue() {
        return new Queue(SPECIALIZATION_REQUEST_QUEUE, false);
    }
}
