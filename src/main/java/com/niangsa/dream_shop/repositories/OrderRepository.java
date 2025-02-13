package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findyUserId(Long userId);
}
