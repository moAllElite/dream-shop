package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.UserDto;

import java.math.BigDecimal;

public interface ICartService {
    CartDto getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeCart(UserDto userDto);
    CartDto getCartByUserId(Long userId);
}
