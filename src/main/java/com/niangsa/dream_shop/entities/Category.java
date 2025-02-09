package com.niangsa.dream_shop.entities;
import  java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;



@Getter @Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "category" ,fetch = FetchType.EAGER)
    private List<Product> products;

    public Category() {
    }

    public Category(String categoryName) {
        this.name = categoryName;
    }
}
