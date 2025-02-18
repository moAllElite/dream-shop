package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    void deleteAllByCartId(Long cartId);

    List<CartItem> findAllByCart(Cart cart);

    //  void deleteAllByCart(Cart cart);
}
