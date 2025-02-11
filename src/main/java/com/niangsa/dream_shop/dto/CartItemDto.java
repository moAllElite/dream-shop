package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.Product;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private String quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Cart cart;
    private Product product;
}
