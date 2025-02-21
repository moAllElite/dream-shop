package com.niangsa.dream_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@RequiredArgsConstructor
@Getter@Setter
public class CartDto {
    private   Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items;

}
