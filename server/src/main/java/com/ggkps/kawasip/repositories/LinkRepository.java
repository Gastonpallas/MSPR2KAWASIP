package com.ggkps.kawasip.repositories;

import com.ggkps.kawasip.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Integer> {

    List<Link> findByOrderId(Integer id);
}
