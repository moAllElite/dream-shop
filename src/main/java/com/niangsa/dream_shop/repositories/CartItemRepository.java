package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId AND c.product.id = :productId")
    void deleteCartItemByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);


}
