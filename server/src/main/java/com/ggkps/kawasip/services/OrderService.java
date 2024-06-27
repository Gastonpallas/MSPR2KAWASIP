package com.ggkps.kawasip.services;

import com.ggkps.kawasip.repositories.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void deleteOrdersByCustomerId(Integer customerId) {
        orderRepository.deleteByCustomerId(customerId);
    }

}
