package com.niangsa.dream_shop.repository;

import com.niangsa.dream_shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
