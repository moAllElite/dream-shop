package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Product;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private Product product;
    private OrderDto orderDto;

}
