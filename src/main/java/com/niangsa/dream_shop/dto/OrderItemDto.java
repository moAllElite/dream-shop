package com.niangsa.dream_shop.dto;

 import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private ProductDto product;
    private OrderDto order;
}
