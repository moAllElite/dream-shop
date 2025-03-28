package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private int quantity;
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "order",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
