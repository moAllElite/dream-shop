package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Product;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private Product product;
    private OrderDto orders;



    public OrderItemDto(Product product, OrderDto orders,int quantity ,BigDecimal price) {
        this.product = product;
        this.orders = orders;
        this.quantity = quantity;
        this.price = price;
    }


    public OrderItemDto(Long id, int quantity, BigDecimal price, Product product, OrderDto orders) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.orders = orders;
    }
}
