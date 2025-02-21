package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
public class ProductDto {
    private  Long id;
    @NotNull(message = "Le nom du produit est obligatoire")
    private String name;
    private String description;
    @NotNull(message = "Le prix est obligatoire")
    @Size(message = "Le prix minimum est de 100",min = 100)
    private BigDecimal price;
    @NotNull(message = "L'inventaire est obligatoire")
    private int inventory;
   @NotNull(message = "La marque est obligatoire")
    private String brand;
    @NotNull(message = "La catégorie est obligatoire")
    private Category category;
    private List<ImageDto> images;
}
