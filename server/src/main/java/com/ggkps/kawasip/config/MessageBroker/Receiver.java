package com.ggkps.kawasip.config.MessageBroker;

import com.ggkps.kawasip.services.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }


    @RabbitListener(queues = RabbitMQ.DELETE_USER_QUEUE)
    public void receiveUserDelete(Integer idToDelete) {
        System.out.println("Received User Update: " + idToDelete);

        orderService.deleteOrdersByCustomerId(idToDelete);

    }

}
