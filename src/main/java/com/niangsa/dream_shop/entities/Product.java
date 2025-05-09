package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.math.BigDecimal;
import java.util.List;
@EnableSpringDataWebSupport
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;
    @OneToMany(mappedBy = "product",cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.PERSIST},orphanRemoval = true)
    private List<CartItem> cartItems;
}
