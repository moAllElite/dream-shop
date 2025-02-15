package com.niangsa.dream_shop.entities;

import com.niangsa.dream_shop.enums.OrderStatuts;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private Long id;
    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatuts orderStatuts;
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
