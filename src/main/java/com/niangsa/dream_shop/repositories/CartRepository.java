package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findCartByUserId(Long userId);
}
