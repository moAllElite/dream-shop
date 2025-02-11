package com.niangsa.dream_shop.repository;

import com.niangsa.dream_shop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    void deleteAllById(Long id);
}
