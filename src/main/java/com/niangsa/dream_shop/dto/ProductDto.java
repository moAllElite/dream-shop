package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;
import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
public class ProductDto {
    private  Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @NotNull(message = "L'inventory est obligatoire")
    private String inventory;
    @NotNull(message = "La marque est obligatoire")
    private String brand;
    private Category category;
    private List<ImageDto> images;
}
