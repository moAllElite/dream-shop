package com.niangsa.dream_shop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.Set;

@RequiredArgsConstructor
@Getter@Setter
public class CartDto {
    private   Long id;
    private  String totalAmount;
    private Set<CartItemDto> cartItems;
}
