package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("from Cart c  where c.user.id =:userId")
    Cart findCartByUserId(Long userId);
}
