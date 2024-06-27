package com.ggkps.kawasip.repositories;

import com.ggkps.kawasip.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer id);

    void deleteByCustomerId(Integer customerId);

}
