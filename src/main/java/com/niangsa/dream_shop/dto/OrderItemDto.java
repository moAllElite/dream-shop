package com.niangsa.dream_shop.dto;

 import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private ProductDto product;
}
