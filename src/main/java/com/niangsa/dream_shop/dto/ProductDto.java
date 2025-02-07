package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private  Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String invetory;
    private String brand;
    private Category category;
}
