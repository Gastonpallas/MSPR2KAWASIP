package com.ggkps.kawasip.repositories;

import com.ggkps.kawasip.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByOrderId(Integer id);
}
