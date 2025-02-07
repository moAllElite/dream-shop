package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private List<Product> products;
}
