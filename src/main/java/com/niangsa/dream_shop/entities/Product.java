package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
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
    private String invetory;
    @ManyToOne()
    private Category category;
    @OneToMany(mappedBy = "product",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Image> images;
}
