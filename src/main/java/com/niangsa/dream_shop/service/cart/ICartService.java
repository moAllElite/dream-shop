package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;

import java.math.BigDecimal;

public interface ICartService {
    CartDto getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeCart();
    CartDto getCartByUserId(Long userId);
}
