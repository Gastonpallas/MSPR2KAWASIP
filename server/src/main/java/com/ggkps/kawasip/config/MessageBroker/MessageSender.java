package com.ggkps.kawasip.config.MessageBroker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQ.QUEUE_NAME, message);
    }

    public void sendUserDelete(Integer idToDelete) {
        rabbitTemplate.convertAndSend(RabbitMQ.DELETE_USER_QUEUE, idToDelete);
    }
}
