package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
