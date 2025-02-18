package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);

}
