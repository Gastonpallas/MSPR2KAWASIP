package com.ggkps.kawasip.config.MessageBroker;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    public static final String QUEUE_NAME = "myQueue";

    public static final String DELETE_USER_QUEUE = "deleteUserQueue";


    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Queue deleteUserQueue() {
        return new Queue(DELETE_USER_QUEUE, false);
    }
}
