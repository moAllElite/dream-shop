package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product, Order order, int quantity, BigDecimal unitPrice) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.price = unitPrice;
    }
}
