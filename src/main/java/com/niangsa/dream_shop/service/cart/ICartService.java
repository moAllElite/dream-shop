package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.User;

import java.math.BigDecimal;

public interface ICartService {
    CartDto getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeCart(User user);
    Cart getCartByUserId(Long userId);
}
